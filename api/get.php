<?php	#https://www.matjazcerkvenik.si/developer/java-json.php

if(isset($_GET["catch"])){

	$json = $_GET["catch"];
	$obj = json_decode($json);
	$host_name = $obj -> {"host_name"};
	$session_username = $obj -> {"session_username"};
	$ip_address = $obj -> {"ip_address"};
	$mac_address = $obj -> {"mac_address"};
	$os_name = $obj -> {"os_name"};
	$os_type = $obj -> {"os_type"};
	$cpu_name = $obj -> {"cpu_name"};
	$gpu_name_1 = $obj -> {"gpu_name_1"};
	$gpu_name_2 = $obj -> {"gpu_name_2"};
	$storage_1 = $obj -> {"storage_1"};
	$storage_2 = $obj -> {"storage_2"};
	$storage_3 = $obj -> {"storage_3"};
	$ram_total = $obj -> {"ram_total"};
	$ram_avaliable = $obj -> {"ram_avaliable"};
	$free_swap_size = $obj -> {"free_swap_size"};
	$bios_manufacturer = $obj -> {"bios_manufacturer"};
	$bios_version = $obj -> {"bios_version"};
	$bios_date = $obj -> {"bios_date"};

	//Hacer un query a la BD y si la mac address o serie de la BIOS ya esta registrada, hacer un UPDATE al registro para ver si hubo cambios tanto de hardware como de software, sino entonces registrar lo como nuevo
	
	$array = array(
				"host_name" => $host_name, 
				"session_username" => $session_username, 
				"ip_address" => $ip_address, 
				"mac_address" => $mac_address,
				"os_name" => $os_name,
				"os_type" => $os_type,
				"cpu_name" => $cpu_name,
				"gpu_name_1" => $gpu_name_1,
				"gpu_name_2" => $gpu_name_2,
				"storage_1" => $storage_1,
				"storage_2" => $storage_2,
				"storage_3" => $storage_3,
				"ram_total" => $ram_total,
				"ram_avaliable" => $ram_avaliable,
				"free_swap_size" => $free_swap_size,
				"bios_manufacturer" => $bios_manufacturer,
				"bios_version" => $bios_version,
				"bios_date" => $bios_date
			);
	echo json_encode($array);
	
	//Conexion a la BD	
	$mysqli = new mysqli("localhost", "root", "", "mypc2inventory");
	/* verificar la conexión */
	if (mysqli_connect_errno()) {
		printf("Conexion fallida: %s\n", mysqli_connect_error());
		exit();
	} # else { echo "Hay conexion"; }
	
	if ($result = $mysqli->query("SELECT mac_address FROM mpi_pc WHERE mac_address='".$mac_address."'")) {

		/* determinar el número de filas del resultado */
		$row_count = $result->num_rows;

		if($row_count > 0){
			#UPDATE
			$mysqli->query("UPDATE mpi_pc SET 
			host_name='".$host_name."', session_username='".$session_username."', ip_address='".$ip_address."', mac_address='".$mac_address."', os_name='".$os_name."', os_type='".$os_type."', cpu_name='".$cpu_name."', gpu_name_1='".$gpu_name_1."', gpu_name_2='".$gpu_name_2."', storage_1='".$storage_1."', storage_2='".$storage_2."', storage_3='".$storage_3."', ram_total='".$ram_total."', ram_avaliable='".$ram_avaliable."', free_swap_size='".$free_swap_size."', bios_manufacturer='".$bios_manufacturer."', bios_version='".$bios_version."', bios_date='".$bios_date."' WHERE mac_address='".$mac_address."'");
			echo "\n Your PC was already registered, so your PC info was updated.";
		} else {
			#ADD NEW
			$mysqli->query("INSERT INTO mpi_pc SET 
			host_name='".$host_name."', session_username='".$session_username."', ip_address='".$ip_address."', mac_address='".$mac_address."', os_name='".$os_name."', os_type='".$os_type."', cpu_name='".$cpu_name."', gpu_name_1='".$gpu_name_1."', gpu_name_2='".$gpu_name_2."', storage_1='".$storage_1."', storage_2='".$storage_2."', storage_3='".$storage_3."', ram_total='".$ram_total."', ram_avaliable='".$ram_avaliable."', free_swap_size='".$free_swap_size."', bios_manufacturer='".$bios_manufacturer."', bios_version='".$bios_version."', bios_date='".$bios_date."' ");
			echo "\n Registered";
		}

		/* cerrar el resultset */
		$result->close();
	}

	$mysqli->close();

}
?>