<?php

	require "init.php";
	$category=$_GET["category"];

	$sql="select * from product where category='$category'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'category'=>$row['category'],'name' =>$row['name'],
	  	'unit'=>$row['unit'],'beforeDiscount'=>$row['beforeDiscount'],'afterDiscount' =>$row['afterDiscount'],'qtn' =>$row['qtn'],'image' =>base64_encode($row['image']),'sells' =>$row['sells']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>