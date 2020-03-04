<?php

	require "init.php";
	$id=$_GET["id"];
	$sql="select * from customer where id='$id'";
	$result=mysqli_query($con,$sql);
	$response=array();
	while ($row=mysqli_fetch_array($result)) {
	  array_push($response,array('id'=>$row['id'],'name' =>$row['name'],'email' =>$row['email'],
	  'password'=>$row['password'],'contact'=>$row['contact'],'address'=>$row['address']
		,'question'=>$row['question'],'answer'=>$row['answer']));
	}
	echo json_encode($response);
	mysqli_close($con);

?>