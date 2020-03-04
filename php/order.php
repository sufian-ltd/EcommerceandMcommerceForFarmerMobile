<?php

require "init.php";
$userId=$_GET["userId"];
$productId=$_GET["productId"];
$productName=$_GET["productName"];
$qtn=$_GET["qtn"];
$cost=$_GET["cost"];
$orderDate=$_GET["orderDate"];
$deliveryDate=$_GET["deliveryDate"];
$status=$_GET["status"];
$payment=$_GET["payment"];
$userStatus="None";
$supplierStatus="None";

$sql="insert into orders (userId,productId,productName,qtn,cost,orderDate,deliveryDate,status,supplierStatus,userStatus,payment) 
values('$userId','$productId','$productName','$qtn','$cost','$orderDate','$deliveryDate','$status','$supplierStatus','$userStatus','$payment');";

if(mysqli_query($con, $sql)){
	$status="inserted";
}
else{
	$status="not inserted";
}

$sql="update product set qtn=qtn-'$qtn' where id='$productId'"; 
mysqli_query($con, $sql);

echo json_encode(array("response"=>$status));
mysqli_close($con);

?>