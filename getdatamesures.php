<?php
$con=mysqli_connect("localhost","root","","steg_app");
$l=$_POST["compteur"];
$res=mysqli_query($con,"select * from mesure where compteur='$l'  ");
$i=0;
while($r=mysqli_fetch_array($res,MYSQLI_NUM))
{
$response[$i]["montant"]=$r[4];
$response[$i]["date"]=$r[1];
$response[$i]["heure"]=$r[2];
$response[$i]["val"]=$r[3];
$i=$i+1;
}

	

echo json_encode($response);
?>