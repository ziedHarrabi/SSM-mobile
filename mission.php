<?php
$con=mysqli_connect("localhost","root","","steg_app");
$c=$_POST["cin"];
$res=mysqli_query($con,"select * from mission where numagent='$c'  and etat='en attente' ");
$i=0;
while($r=mysqli_fetch_array($res,MYSQLI_NUM))
{
$response[$i]["compteur"]=$r[0];
$response[$i]["lat"]=	$r[4];
$response[$i]["lon"]=	$r[5];
$i=$i+1;
}
echo json_encode($response);
?>