<?php
$con=mysqli_connect("localhost","root","","steg_app");
$l=$_POST["num"];
$res=mysqli_query($con,"select * from mesure,client where mesure.compteur='$l' and client.compteur='$l'  order by mesure.date desc limit 1");

if($r=mysqli_fetch_array($res,MYSQLI_NUM))
{
$response["dermesure"]=$r[3];
$response["nom"]=$r[6];
$response["tel"]=$r[7];

}

	

echo json_encode($response);
?>