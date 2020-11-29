<?php
$con=mysqli_connect("localhost","root","","steg_app");
$n=$_POST["lat"];
$x=$_POST["lon"];
$nm=$_POST["etat"];
$res=mysqli_query($con,"update mission set etat='$nm' where latitude='$n' and longitude='$x'");

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