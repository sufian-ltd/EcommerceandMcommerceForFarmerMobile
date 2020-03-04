<?php

	require "init.php";
	
	$orderId=$_GET["orderId"];
	$userStatus="Product Received";
	$sql="update orders set userStatus='$userStatus' where id='$orderId'"; 
	if(mysqli_query($con, $sql)){
		$status="saved";
	}
	else{
		$status="not saved";
	}

	echo json_encode(array("response"=>$status));
	mysqli_close($con);

?>