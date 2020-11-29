<?php
$con=mysqli_connect("localhost","root","","steg_app");
$c=$_POST["compteur"];
$res=mysqli_query($con,"select * from facture where compteur='$c' ");
$i=0;
while($r=mysqli_fetch_array($res,MYSQLI_NUM))
{
$response[$i]["num_facture"]=$r[1];
$i=$i+1;
}
echo json_encode($response);
?>