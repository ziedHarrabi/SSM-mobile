<?php
$con=mysqli_connect("localhost","root","","steg_app");
$l=$_POST["log"];
$p=$_POST["pass"];
$res=mysqli_query($con,"select * from client where compteur='$l' and password='$p' ");

if($r=mysqli_fetch_array($res,MYSQLI_NUM))
{
$response["name"]=$r[1];
$response["type"]="Client";
$response["num"]=$r[0];
}
else
{
	$res1=mysqli_query($con,"select * from agent where login='$l' and password='$p' ");

if($r1=mysqli_fetch_array($res1,MYSQLI_NUM))
{
$response["name"]=$r1[1];
$response["type"]="Agent";
$response["num"]=$r1[0];
}
else
{
	$response["name"]="error";
$response["type"]="error";
$response["num"]="error";
	
}
	
}
echo json_encode($response);
?>