<?php
  class conBD {
    public $conexion = null;
    public $last_id_Insert=-1;
    public $srv;
    public $bd;
    public $usr;
    public $clv;
    private $resultadoTXT = "Resultados";
    private $ultimoInsertado = "ultimoInsertado";

    function __construct($servidor, $baseDatos, $usuario, $clave) {
      $this->srv = $servidor;
      $this->bd = $baseDatos;
      $this->usr = $usuario;
      $this->clv = $clave;
    }


    function conectar() {
      $this->conexion= mysqli_connect($this->srv, $this->usr, $this->clv, $this->bd);

      if( !$this->conexion ) {
        return false;
      }
      else {
        return true;
      }
    }

    function cerrar() {
      $conexion->close();
    }

    function insertar($sql) {
      if($conexion->query($sql)===true) {
        $last_id_Insert = $conexion->insert_id;
        return true;
      }
      else {
        return false;
      }
    }
    function getError() {
      return $conexion->error;
    }

    function consultarMAC($sql) {
      $result = mysqli_query($this->conexion, $sql);
      if($result->num_rows > 0) {
        return true;
      }
      return false;
    }

    function consultar($sql) {
      $result = mysqli_query($this->conexion, $sql);
      if($result->num_rows > 0) {
        $output[] = array($this->resultadoTXT=>$result->num_rows);
        while($row = $result->fetch_assoc()) {
          foreach($row as $a => $b)
            $row[$a] = $b;
            $output[] = $row;
        }
        return json_encode($output);

      }
      else {
        return json_encode(array(array($this->resultadoTXT=>0) ) );
      }

    }

    function actualizar($sql) {
      $result = mysqli_query($this->conexion, $sql);

      $output[] = array($this->resultadoTXT=>mysqli_affected_rows($this->conexion) );
      $output[] = array($this->ultimoInsertado=>mysqli_affected_rows($this->conexion) );
      return json_encode($output);
    }

    function eliminar($sql) {
      $result = mysqli_query($this->conexion, $sql);

      $output[] = array($this->resultadoTXT=>mysqli_affected_rows($this->conexion) );
      $output[] = array($this->ultimoInsertado=>mysqli_affected_rows($this->conexion) );
      return json_encode($output);
    }

    function consultarInsert($sql) {
      $result = mysqli_query($this->conexion, $sql);
      $last_id_Insert = mysqli_insert_id($this->conexion);
      $output[] = array($this->resultadoTXT=>1);
      $output[] = array($this->ultimoInsertado=>$last_id_Insert);
      return json_encode($output);



    }

  }
 ?>
