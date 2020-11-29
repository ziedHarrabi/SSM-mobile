<?php
$con=mysqli_connect("localhost","root","","steg_app");
$n=$_POST["num"];
$nm=$_POST["newmesure"];
$d=$_POST["diff"];
$m=$_POST["montant"];
$d=Date("Y-M-d");
$h=Date("H:i:s");
$res=mysqli_query($con,"insert into mesure values('$n','$d','$h','$nm','$m')");
$res1=mysqli_query($con,"update mission set etat='fini' where compteur='$n'");
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