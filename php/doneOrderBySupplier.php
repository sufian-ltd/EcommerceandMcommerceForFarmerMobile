<?php

	require "init.php";
	
	$orderId=$_GET["orderId"];
	$supplierId=$_GET["supplierId"];

	$supplierStatus="Delivery Done";
	$sql="update orders set supplierStatus='$supplierStatus' where id='$orderId'"; 
	if(mysqli_query($con, $sql)){
		$status="saved";
	}
	else{
		$status="not saved";
	}

	$sql2="update supplier set orderId=0 where id='$supplierId'"; 
	mysqli_query($con, $sql2);

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>