<?php
$con=mysqli_connect("localhost","root","","steg_app");
$c=$_POST["compteur"];
$n=$_POST["nom"];
$t=$_POST["tel"];
$a=$_POST["adresse"];
$p=$_POST["password"];
$res=mysqli_query($con,"insert into client values ('$c','$n','$t','$a','$p')");

if($res==1)
{
$response["flag"]="ok";
}
else
{
$response["flag"]="no";
}	
echo json_encode($response);
?>