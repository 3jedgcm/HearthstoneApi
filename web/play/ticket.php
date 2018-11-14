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
    $stat = $leMagasinDesTickets->useACredit($user->id_user);

    if ($stat == 0) {
        $tickets = $leMagasinDesTickets->getTicketSimple($user->id_user);
        $ticket = $tickets[0];
        $gain = $tickets[1];
    } else {
        $error = "Aucun Crédit";
    }
}

$title = "Magasin des tickets";
$background = "url('../src/img/background_3.jpg')";

/*Style*/
$style = "<link href='/src/css/ticket.css' rel='stylesheet' type='text/css'>";

ob_start(); ?>
<div class="ui centered grid one column container segment custom-noSelect">
    <div class="center aligned column">
        <h2 class="ui header">Jouer</h2>
        <?php
        if (isset($_POST["play"]) && $stat != -1)
        {?>
            <div>
                <div class="card"><div id="Case1" class="sc__container"></div></div>
                <div class="card"><div id="Case2" class="sc__container"></div></div>
                <div class="card"><div id="Case3" class="sc__container"></div></div>
                <div class="card"><div id="Case4" class="sc__container"></div></div>
                <div class="card"><div id="Case5" class="sc__container"></div></div>
                <div class="card"><div id="Case6" class="sc__container"></div></div>
            </div>
        <?php
        }?>
        <p>Vous avez <span class="credit"><?= $user->getCredit() ?></span> crédit<?= ($user->getCredit() > 1 ? 's' : ''); ?></p>
            <form role="form"  method="post" id="login-form" autocomplete="off">
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
    </div>
    <div class="center aligned column">
        <h2 class="ui header">Gains sur ticket</h2>
        <table class="ui very basic collapsing celled striped fixed padded table">
            <thead>
            <tr>
                <th class="center aligned">Ticket</th>
                <th class="center aligned">Gains</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td class="ui tiny images">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                </td>
                <td class="right aligned"><?=SIXTICKETGAIN?> Crédits</td>
            </tr>
            <tr>
                <td class="ui tiny images">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                </td>
                <td class="right aligned"><?=FIVETICKETGAIN?> Crédits</td>
            </tr>
            <tr>
                <td class="ui tiny images">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                </td>
                <td class="right aligned"><?=FOURTICKETGAIN?> Crédits</td>
            </tr>
            <tr>
                <td class="ui tiny images">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                </td>
                <td class="right aligned"><?=THREETICKETGAIN?> Crédits</td>
            </tr>
            <tr>
                <td class="ui tiny images">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                </td>
                <td class="right aligned"><?=TWOTICKETGAIN?> Crédits</td>
            </tr>
            <tr>
                <td class="ui tiny images">
                    <img class="ui image" src="../src/img/ticket/win_1.png">
                </td>
                <td class="right aligned"><?=ONETICKETGAIN?> Crédit </td>
            </tr>
            <tr>
                <td class="ui tiny images">
                    <img class="ui image" src="../src/img/ticket/empty.png">
                </td>
                <td class="right aligned">0 Crédit </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<?php $content = ob_get_clean();

/*Script*/
ob_start(); ?>
<?php
if(isset($_POST["play"]))
{
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
                $tabPlace[$i] = rand(0, 5);
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
    <script>
        credit = <?= $user->getCredit() ?>;
        winCount = 0;
        $(document).ready(function () {
            <?php
            for($i = 0; $i < 6; $i++)
            {
                $bg = "/src/img/ticket/lose_". $tabPlace[$i] . ".png";
                $fg = "/src/img/ticket/lucky.jpg";
                if($tabPlace[$i] == 0){
                    $bg = "/src/img/ticket/win_1.png";
                }
                ?>
                    sc = new ScratchCard('#Case<?=$i+1?>', {
                        scratchType: SCRATCH_TYPE.CIRCLE,
                        containerWidth: 100,
                        containerHeight: 100,
                        imageForwardSrc: '<?=$fg?>',
                        htmlBackground:"<div class=\"bg\" style=\"background-image : url(\'<?=$bg?>\')\"></div>",
                        clearZoneRadius: 15,
                        percentToFinish: 50,
                        callback :function () {
                            <?php
                            if($tabPlace[$i] == 0){?>
                                winCount++;
                                var n = credit;
                                switch (winCount) {
                                    case 1 :
                                        n+=<?=ONETICKETGAIN?>;
                                        break;
                                    case 2 :
                                        n+=<?=TWOTICKETGAIN?>;
                                        break;
                                    case 3 :
                                        n+=<?=THREETICKETGAIN?>;
                                        break;
                                    case 4 :
                                        n+=<?=FOURTICKETGAIN?>;
                                        break;
                                    case 5 :
                                        n+=<?=FIVETICKETGAIN?>;
                                        break;
                                    case 6 :
                                        n+=<?=SIXTICKETGAIN?>;
                                        break;
                                }
                                $('.credit').text(n);
                            <?php
                            }?>
                        }
                    });
                    sc.init().then(() => {
                        sc.canvas.addEventListener('scratch.move', () => {
                            this.percent = sc.getPercent().toFixed(1)
                        })
                    });
                <?php
            }?>
        });
    </script>
<?php
}
$script = ob_get_clean();
include_once('../include/template.php'); ?>