<?php
$con=mysqli_connect("localhost","root","","steg_app");
$c=$_POST["cin"];
$l1=$_POST["lat"];
$l2=$_POST["lon"];
$res=mysqli_query($con,"update agent set latitude='$l1', longitude='$l2' where cin='$c' ");

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