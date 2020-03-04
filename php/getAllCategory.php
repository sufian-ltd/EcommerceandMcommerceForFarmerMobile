<?php

	require "init.php";
	$sql="select * from category";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	    $cat=$row['name'];
	    $sqlPro="select count(id) as id from product where category='$cat'";
	    $tpRes=mysqli_query($con,$sqlPro);
	    $totalProduct=mysqli_fetch_array($tpRes);
	    array_push($response,array('id'=>$row['id'],'name' =>$row['name'],'status' =>$row['status'],
	    'totalProduct'=>$totalProduct['id']));
	}
	echo json_encode($response);
	mysqli_close($con);
?>