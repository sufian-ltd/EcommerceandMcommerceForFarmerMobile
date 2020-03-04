<?php

	require "init.php";
	$id=$_GET["id"];

	$sql="select * from orders where id='$id'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'userId'=>$row['userId'],'productId' =>$row['productId'],'productName' =>$row['productName'],
	  	'qtn'=>$row['qtn'],'cost'=>$row['cost'],'orderDate' =>$row['orderDate'],'deliveryDate' =>$row['deliveryDate'],'status' =>$row['status'],'supplierStatus' =>$row['supplierStatus'],'userStatus' =>$row['userStatus'],'payment' =>$row['payment']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>