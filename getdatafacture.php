<?php
$con=mysqli_connect("localhost","root","","steg_app");
$c=$_POST["compteur"];
$f=$_POST["facture"];
$res=mysqli_query($con,"select * from facture where compteur='$c' and numfacture='$f' ");

if($r=mysqli_fetch_array($res,MYSQLI_NUM))
{
$response["dd"]=$r[2];
$response["df"]=$r[3];
$response["montant"]=$r[4];
$response["nb"]=$r[5];
$response["delai"]=$r[6];
}
echo json_encode($response);
?>