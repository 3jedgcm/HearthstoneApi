<?php
include_once "../include/logged.php";

$lesGamesDAO = new gameDAO(MaBD::getInstance());
$Game = $lesGamesDAO->getOne('Les tickets chanceux de Castor futé');

if ($Game->getEtat() == 0) {
    header('Location: /');
}

$stat = 0;

if (isset($_POST["play"])) {

    $leMagasinDesTickets = new ticketDAO(MaBD::getInstance());
    $newMoney = $leMagasinDesTickets->useACredit($user->id_user);

    if ($newMoney > 0) {
        $tickets = $leMagasinDesTickets->getTicketSimple($user->id_user);
        $ticket = $tickets[0];
        $gain = $tickets[1];
    } else {
        $error = "Aucun Crédit";
    }
}
else
{
  $newMoney["credit"] = $user->getCredit();
}

$title = "Magasin des tickets";
$background = "url('../src/img/background_3.jpg')";

/*Style*/
$style = "<link href='/src/css/ticket.css' rel='stylesheet' type='text/css'>";

ob_start(); ?>
    <section class="ui middle aligned center aligned grid custom-fullHeight custom-noSelect">
        <div class="doubling two column row">
            <div class="eight wide column">
                <div class="ui segment stackable middle aligned center aligned custom-margin0">
                    <h2 class="ui header">Jouer</h2>
                    <?php
                    if (isset($_POST["play"]) && $stat != -1) {
                        ?>
                        <div>
                            <?php
                            for ($i = 1; $i <= 6; $i++) {
                                ?>
                                <div class="card">
                                    <div id="Case<?= $i ?>" class="sc__container"></div>
                                </div>
                                <?php
                            } ?>
                        </div>
                        <?php
                    } ?>
                    <p>Vous avez <span class="credit"><?php echo $newMoney["credit"]; ?></span>
                        crédit<?= ($user->getCredit() > 1 ? 's' : ''); ?></p>

                    <form role="form" method="post" id="login-form" autocomplete="off">
                        <button class="ui animated fade orange button" name="play" type="submit" tabindex="0">
                            <div class="visible content">Acheter</div>
                            <div class="hidden content">1 crédit</div>
                        </button>
                    </form>
                    <?php
                    if ($stat == -1) {
                        echo '
                            <div class="ui negative message">
                                <div class="header">Credit insuffisant !</div>
                                <p>Passez à la banque</p>
                            </div>';
                    }
                    ?>
                    <h2 class="ui header">Gains sur ticket</h2>
                    <table class="ui very basic collapsing celled striped fixed padded table custom-white-background custom-center">
                        <thead>
                        <tr>
                            <th class="center aligned">Ticket</th>
                            <th class="center aligned">Gains</th>
                        </tr>
                        </thead>
                        <tbody>
                        <?php
                        for ($i = 6; $i >= 0; $i--) {
                            $g = 0;
                            switch ($i) {
                                case 6 :
                                    $g = SIXTICKETGAIN-1;
                                    break;
                                case 5 :
                                    $g = FIVETICKETGAIN-1;
                                    break;
                                case 4 :
                                    $g = FOURTICKETGAIN-1;
                                    break;
                                case 3 :
                                    $g = THREETICKETGAIN-1;
                                    break;
                                case 2 :
                                    $g = TWOTICKETGAIN-1;
                                    break;
                                case 1 :
                                    $g = ONETICKETGAIN-1;
                                    break;
                            }
                            ?>
                            <tr>
                                <td class="ui tiny images">
                                    <?php
                                    if ($i > 0) {
                                        for ($j = $i; $j > 0; $j--) {
                                            ?>
                                            <img class="ui image" src="../src/img/ticket/win_1.png">
                                            <?php
                                        }
                                    } else { ?>
                                        <img class="ui image" src="../src/img/ticket/empty.png">
                                        <?php
                                    } ?>
                                </td>
                                <td class="right aligned"><?= $g ?> Crédits</td>
                            </tr>
                            <?php
                        } ?>
                        </tbody>
                    </table>
                </div>
            </div>
    </section>
<?php $content = ob_get_clean();

/*Script*/
ob_start(); ?>
<?php
if (isset($_POST["play"])) {
    $tabPlace = array("0", "0", "0", "0", "0", "0");
    $nonGagnant = 6 - $ticket;
    $gagnant = $ticket;
    for ($i = 0; $i < 6; $i++) {
        if ($gagnant != 0 && $nonGagnant != 0) {
            $place = rand(-1, 1);
            if ($place == 0) {
                $tabPlace[$i] = 0;
                $gagnant = $gagnant - 1;
            } else {
                $tabPlace[$i] = rand(1, 5);
                $nonGagnant = $nonGagnant - 1;
            }
        } else if ($gagnant != 0 && $nonGagnant == 0) {
            $tabPlace[$i] = 0;
            $gagnant = $gagnant - 1;
        } else if ($gagnant == 0 && $nonGagnant != 0) {
            $tabPlace[$i] = rand(1, 5);
            $nonGagnant = $nonGagnant - 1;
        }
    } ?>
    <script type="text/javascript" src="/src/js/lib/scratchcard.min.js"></script>
    <script type="text/javascript" src="/src/js/ticket.js"></script>
    <script>
        credit = <?php echo $newMoney["credit"]; ?>;
        winCount = 0;

        $(document).ready(function () {
            <?php
            for($i = 0; $i < 6; $i++)
            {
            $bg = "/src/img/ticket/lose_" . $tabPlace[$i] . ".png";
            $fg = "/src/img/ticket/lucky.jpg";
            if ($tabPlace[$i] == 0) {
                $bg = "/src/img/ticket/win_1.png";
            }
            $call = $tabPlace[$i] == 0 ? "function(){displayNewScore()}" : "function(){}"
            ?>
                displayScratch(<?=$i+1?>,"<?=$bg?>","<?=$fg?>",<?=$call?>);
            <?php
            }?>
        });

        displayNewScore = function() {
            winCount++;
            var n = credit;
            switch (winCount) {
                case 1 :
                    n +=<?=ONETICKETGAIN?>;
                    break;
                case 2 :
                    n +=<?=TWOTICKETGAIN?>;
                    break;
                case 3 :
                    n +=<?=THREETICKETGAIN?>;
                    break;
                case 4 :
                    n +=<?=FOURTICKETGAIN?>;
                    break;
                case 5 :
                    n +=<?=FIVETICKETGAIN?>;
                    break;
                case 6 :
                    n +=<?=SIXTICKETGAIN?>;
                    break;
            }
            $('.credit').text(n);
        };
    </script>
    <?php
}
$script = ob_get_clean();
include_once('../include/template.php'); ?>
