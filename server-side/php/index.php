<?php

  require("bd.php");
  $servidor="localhost";
  $baseDatos = "u281511508_mgm";
  $usuario = "u281511508_mgm";
  $clave = "nvs29rtFmKK9";

  $bdO = new conBD($servidor, $baseDatos, $usuario, $clave);
  $now = "CONVERT_TZ(NOW(),'+0:00','-5:00')";

  if( $bdO->conectar() === true ) {
    $consulta = "";

    foreach ($_POST as $key => $entry)
    {
         $consulta = $consulta.$key . "=>" . $entry . "\n";
    }
    $bdO->consultar("INSERT INTO consultas(consulta, fecha, idClinica, mac, idPersonalLogIn, actividad) VALUES('{$consulta}', {$now}, {$_POST['idClinica']}, '{$_POST['mac']}', {$_POST['idPersonalLogIn']}, '{$_POST['Actividad']}')");


    switch($_POST['Actividad']) {
      case "Activar MAC":
        if($bdO->consultarMAC("SELECT * FROM `personal` WHERE usuario = '{$_POST['usuario']}' AND clave = '{$_POST['clave']}' AND tipo = 0") ) {

          echo $bdO->consultarInsert("INSERT INTO macs(mac) VALUES('{$_POST['mac']}')");
        }
        else {
          echo json_encode(array(array('Resultados'=>0) ) );
        }

        break;


      case "InicioSesion":
        if($bdO->consultarMAC("SELECT * FROM `macs` WHERE mac = '{$_POST['mac']}'") ) {

          echo $bdO->consultar("SELECT personal.*, clinicas.titulo FROM `personal`, `clinicas` WHERE clinicas.idClinica = {$_POST['idClinica']} ".
                          " and personal.usuario = '".$_POST["usuario"]."'".
                          " and personal.clave = '".$_POST["clave"]."'"
                        );
        }
        else {
          echo json_encode(array(array('Resultados'=>0) ) );
        }

        break;


        case "Clinicas: Lista":
          echo $bdO->consultar("SELECT * FROM `clinicas` ORDER BY nombre ASC");
          break;
      case "TiposProductos: Lista":
        echo $bdO->consultar("SELECT * FROM `tiposProductos`  ORDER BY clave");
        break;
      case "TiposProductos: Eliminar":
        echo $bdO->eliminar("DELETE  FROM `tiposProductos` WHERE tiposProductos.idTipoProducto=".$_POST["idTipoProducto"]);
        break;
      case "TiposProductos: Agregar":
        echo $bdO->consultarInsert("INSERT INTO `tiposProductos` (clave, nombre, descripcion, idClinica) VALUES ('".$_POST["clave"]."', '".$_POST["nombre"]."', '".$_POST["descripcion"]."', ".$_POST["idClinica"].")");
        break;
      case "TiposProductos: Actualizar":
        echo $bdO->actualizar("UPDATE `tiposProductos` SET clave = '".$_POST["clave"]."',  nombre = '".$_POST["nombre"]."', descripcion='".$_POST["descripcion"]."' WHERE idTipoProducto=".$_POST["idTipoProducto"]);
        break;


      case "Productos: Lista":
        echo $bdO->consultar("SELECT * FROM `productos` WHERE productos.idTipoProducto=".$_POST["idTipoProducto"]);
        break;

      case "Productos: Lista con precios":
        echo $bdO->consultar("SELECT precioProductos.precio, precioProductos.idPrecioProducto, productos.* FROM productos, precioProductos, (SELECT max(idPrecioProducto) AS idPrecioProducto FROM precioProductos GROUP BY idProducto) AS pP WHERE pP.idPrecioProducto = precioProductos.idPrecioProducto AND productos.idProducto = precioProductos.idProducto ORDER BY productos.clave ");
        break;
      case "Productos: Lista con precio":
        echo $bdO->consultar("SELECT precioProductos.precio, precioProductos.idPrecioProducto, productos.* FROM productos, precioProductos, (SELECT max(idPrecioProducto) AS idPrecioProducto FROM precioProductos GROUP BY idProducto) AS pP WHERE pP.idPrecioProducto = precioProductos.idPrecioProducto AND productos.idProducto = precioProductos.idProducto AND (productos.clave LIKE '%{$_POST['patron']}%' OR productos.nombre LIKE '%{$_POST['patron']}%') ORDER BY productos.clave ");
        break;
      case "Productos: Lista total":
        echo $bdO->consultar("SELECT * FROM `productos` ORDER BY clave");
        break;
      case "Productos: Lista total con patron":
        echo $bdO->consultar("SELECT * FROM `productos` WHERE clave LIKE '%{$_POST['patron']}%' OR nombre LIKE '%{$_POST['patron']}%' ORDER BY clave");
        break;
      case "Productos: Eliminar":
        echo $bdO->eliminar("DELETE  FROM `productos` WHERE productos.idProducto=".$_POST["idProducto"]);
        break;
      case "Productos: Agregar":
        echo $bdO->consultarInsert("INSERT INTO `productos` (clave, nombre, descripcion, cantidadMinima, barCode, idTipoProducto) VALUES ('".$_POST["clave"]."', '".$_POST["nombre"]."', '".$_POST["descripcion"]."', ".$_POST["cantidadMinima"].", '".addslashes($_POST["barCode"])."', ".$_POST["idTipoProducto"].")");
        break;
      case "Productos: Actualizar":

        echo $bdO->actualizar("UPDATE `productos` SET clave = '".$_POST["clave"]."',  nombre = '".$_POST["nombre"]."', descripcion='".$_POST["descripcion"]."', cantidadMinima=".$_POST["cantidadMinima"].", barCode='".addslashes($_POST["barCode"])."' WHERE idProducto=".$_POST["idProducto"]);
        break;


      case "Tratamientos: Lista con costo":
            echo $bdO->consultar("SELECT * FROM tratamientos, (SELECT idTratamiento, costo FROM costoTratamientos ORDER by fecha DESC LIMIT 0,1) AS costos WHERE tratamientos.idTratamiento = costos.idTratamiento AND tratamientos.idClinica=".$_POST["idClinica"]);
            break;
      case "Tratamientos: Lista":
        echo $bdO->consultar("SELECT * FROM `tratamientos` WHERE tratamientos.idClinica=".$_POST["idClinica"]);
        break;
      case "Tratamientos: Eliminar":
        echo $bdO->consultar("DELETE  FROM `tratamientos` WHERE tratamientos.idTratamiento=".$_POST["idTratamiento"]);
        break;
      case "Tratamientos: Agregar":
        echo $bdO->consultar("INSERT INTO `tratamientos` (nombre, descripcion, sesiones, caducidad, idClinica) VALUES ('{$_POST['nombre']}', '{$_POST['descripcion']}', {$_POST['sesiones']}, {$_POST['caducidad']},  {$_POST['idClinica']})");
        break;
      case "Tratamientos: Actualizar":
        echo $bdO->consultar("UPDATE `tratamientos` SET   nombre = '{$_POST['nombre']}', descripcion='{$_POST['descripcion']}', sesiones={$_POST['sesiones']}, caducidad={$_POST['caducidad']} WHERE idTratamiento=".$_POST["idTratamiento"]);
        break;

        case "Costo Tratamientos: Lista tratamientos":
          echo $bdO->consultar("SELECT  * FROM  `tratamientos`");
          break;
        case "Costo Tratamientos: Lista costos de tratamientos":
          echo $bdO->consultar("SELECT  * FROM  `costoTratamientos` WHERE idTratamiento={$_POST['idTratamiento']} ORDER BY fecha DESC");
          break;
        case "Costo Tratamientos: Agregar":
          echo $bdO->consultar("INSERT INTO `costoTratamientos` (fecha, costo, idTratamiento) VALUES ({$now}, {$_POST['costo']}, {$_POST['idTratamiento']})");
          break;
        case "Costo Tratamientos: Eliminar":
          echo $bdO->consultar("DELETE FROM `costoTratamientos` WHERE idCostoTratamiento= {$_POST['idCostoTratamiento']}");
          break;
        case "Costo Tratamientos: Actualizar":
          echo $bdO->consultar("UPDATE `costoTratamientos` SET costo={$_POST['costo']}  WHERE idCostoTratamiento={$_POST['idCostoTratamiento']}");
          break;

      case "Detalle tratamiento: Lista":
        echo $bdO->consultar("SELECT productos.nombre as producto, detallesTratamientos.* FROM `detallesTratamientos`, `productos` WHERE detallesTratamientos.idProducto=productos.idProducto AND detallesTratamientos.idTratamiento={$_POST['idTratamiento']}");
        break;

      case "Detalle tratamiento: Agregar":
        echo $bdO->consultar("INSERT INTO `detallesTratamientos` (idTratamiento, idProducto, cantidad) VALUES ({$_POST['idTratamiento']}, {$_POST['idProducto']}, {$_POST['cantidad']})");
        break;
      case "Detalle tratamiento: Eliminar":
        echo $bdO->consultar("DELETE  FROM `detallesTratamientos` WHERE idDetalleTratamiento=".$_POST["idDetalleTratamiento"]);
        break;


      case "Pacientes: Lista":
        echo $bdO->consultar("SELECT * FROM `pacientes` ORDER BY nombre ASC");
        break;
      case "Pacientes: Lista con patron":
        echo $bdO->consultar("SELECT * FROM `pacientes`  WHERE nombre LIKE '%{$_POST['patron']}%'");
        break;
      case "Pacientes: Eliminar":
        echo $bdO->consultar("DELETE  FROM `pacientes` WHERE pacientes.idPaciente=".$_POST["idPaciente"]);
        break;
      case "Pacientes: Agregar":
        echo $bdO->consultar("INSERT INTO `pacientes` (nombre, apellidos, email, telefono, movil, clave, huella0, huella1, huella2, huella3, huella4, idClinica) VALUES ('{$_POST['nombre']}', '{$_POST['apellidos']}', '{$_POST['email']}', '{$_POST['telefono']}', '{$_POST['movil']}', '{$_POST['clave']}', '{$_POST['huella0']}', '{$_POST['huella1']}', '{$_POST['huella2']}', '{$_POST['huella3']}', '{$_POST['huella4']}', {$_POST['idClinica']})");
        break;
      case "Pacientes: Agregar rapido":
        echo $bdO->consultarInsert("INSERT INTO `pacientes` (nombre, apellidos, email, telefono, movil, idClinica) VALUES ('{$_POST['nombre']}', '{$_POST['apellidos']}', '{$_POST['email']}', '{$_POST['telefono']}', '{$_POST['movil']}', {$_POST['idClinica']})");
        break;
      case "Pacientes: Actualizar":
        echo $bdO->consultar("UPDATE `pacientes` SET   nombre = '{$_POST['nombre']}', apellidos='{$_POST['apellidos']}', email='{$_POST['email']}', telefono='{$_POST['telefono']}', movil='{$_POST['movil']}', clave='{$_POST['clave']}', huella0='{$_POST['huella0']}', huella1='{$_POST['huella1']}', huella2='{$_POST['huella2']}', huella3='{$_POST['huella3']}', huella4='{$_POST['huella4']}'  WHERE idPaciente=".$_POST["idPaciente"]);
        break;


      case "Personal: Lista":
        echo $bdO->consultar("SELECT * FROM `personal` ");
        break;
      case "Personal: Lista con tipo":
        echo $bdO->consultar("SELECT * FROM `personal` WHERE  tipo={$_POST['tipo']}");
        break;
      case "Personal: Lista con codigo":
        echo $bdO->consultar("SELECT * FROM `personal` WHERE personal.idPersonal ='{$_POST['codigo']}' ");
        break;
      case "Personal: Eliminar":
        echo $bdO->consultar("DELETE  FROM `personal` WHERE personal.idPersonal=".$_POST["idPersonal"]);
        break;
      case "Personal: Agregar":
        echo $bdO->consultar("INSERT INTO `personal` (nombre, apellidos, email, telefono, movil, usuario, clave, huella0, huella1, huella2, huella3, huella4, idClinica, tipo) VALUES ('{$_POST['nombre']}', '{$_POST['apellidos']}', '{$_POST['email']}', '{$_POST['telefono']}', '{$_POST['movil']}','{$_POST['usuario']}', '{$_POST['clave']}',  '{$_POST['huella0']}', '{$_POST['huella1']}', '{$_POST['huella2']}', '{$_POST['huella3']}', '{$_POST['huella4']}', {$_POST['idClinica']}, {$_POST['tipo']})");
        break;
      case "Personal: Actualizar":
        echo $bdO->consultar("UPDATE `personal` SET   nombre = '{$_POST['nombre']}', apellidos='{$_POST['apellidos']}', email='{$_POST['email']}', telefono='{$_POST['telefono']}', movil='{$_POST['movil']}', usuario='{$_POST['usuario']}', clave='{$_POST['clave']}', huella0='{$_POST['huella0']}', huella1='{$_POST['huella1']}', huella2='{$_POST['huella2']}', huella3='{$_POST['huella3']}', huella4='{$_POST['huella4']}', tipo={$_POST['tipo']}  WHERE idPersonal=".$_POST["idPersonal"]);
        break;


      case "Servicios: Lista":
        echo $bdO->consultar("SELECT * FROM `servicios` WHERE servicios.idClinica=".$_POST["idClinica"]);
        break;

      case "Servicios: Eliminar":
        echo $bdO->consultar("DELETE  FROM `servicios` WHERE servicios.idServicio=".$_POST["idServicio"]);
        break;
      case "Servicios: Agregar":
        echo $bdO->consultar("INSERT INTO `servicios` (nombre, descripcion, costo, idClinica) VALUES ('{$_POST['nombre']}', '{$_POST['descripcion']}', {$_POST['costo']},  {$_POST['idClinica']})");
        break;
      case "Servicios: Actualizar":
        echo $bdO->consultar("UPDATE `servicios` SET   nombre = '{$_POST['nombre']}', descripcion='{$_POST['descripcion']}', costo={$_POST['costo']}  WHERE idServicio=".$_POST["idServicio"]);
        break;


      case "Almacen: Lista entradas":
        echo $bdO->consultar("SELECT almacen.*, productos.nombre FROM `almacen`, `productos` WHERE almacen.idClinica = {$_POST['idClinica']} AND almacen.cantidad>0 AND almacen.idProducto = productos.idProducto AND almacen.autorizado=FALSE ORDER BY fecha DESC");
        break;
      case "Almacen: Lista salidas":
        echo $bdO->consultar("SELECT almacen.*, productos.nombre FROM `almacen`, `productos` WHERE almacen.idClinica = {$_POST['idClinica']} AND almacen.cantidad<0 AND almacen.idProducto = productos.idProducto AND almacen.autorizado=FALSE ORDER BY fecha DESC");
        break;
      case "Almacen: Lista con cantidad":
        echo $bdO->consultar("SELECT productos.*, sum(cantidad) as cantidad FROM `productos`, `almacen` WHERE almacen.idClinica = {$_POST['idClinica']} AND productos.idProducto=almacen.idProducto GROUP by idProducto");
        break;


      case "Almacen: Agregar entradas":
        echo $bdO->consultar("INSERT INTO `almacen` (cantidad, fecha, autorizado, idProducto, idClinica) VALUES ({$_POST['cantidad']}, {$now},FALSE, {$_POST['idProducto']}, {$_POST['idClinica']})");
        break;

      case "Almacen: Agregar salidas":
        echo $bdO->consultar("INSERT INTO `almacen` (cantidad, fecha, autorizado, idProducto, idClinica) VALUES (-{$_POST['cantidad']}, {$now}, FALSE, {$_POST['idProducto']}, {$_POST['idClinica']})");
        break;

      case "Almacen: Eliminar":
        echo $bdO->consultar("DELETE FROM `almacen` WHERE almacen.idAlmacen={$_POST['idAlmacen']}");
        break;

      case "Almacen: Autorizar entradas":
        echo $bdO->consultar("UPDATE `almacen` SET   autorizado = TRUE  WHERE almacen.idClinica = {$_POST['idClinica']} AND cantidad>0 AND autorizado=FALSE");
        break;
      case "Almacen: Autorizar salidas":
        echo $bdO->consultar("UPDATE `almacen` SET   autorizado = TRUE  WHERE almacen.idClinica = {$_POST['idClinica']} AND cantidad<0 AND autorizado=FALSE");
        break;


      case "Almacen: Verificar minimos":
        echo $bdO->consultar("SELECT * FROM (SELECT SUM(almacen.cantidad) as cantidadP, productos.idProducto AS idProducto, productos.cantidadMinima AS cantidadMinima FROM productos, almacen WHERE productos.idProducto=almacen.idProducto GROUP BY almacen.idProducto) AS T WHERE T.cantidadP<T.cantidadMinima");
        break;

      case "Precio Productos: Lista productos":
        echo $bdO->consultar("SELECT  * FROM  `productos` ORDER BY clave");
        break;
      case "Precio Productos: Lista precios de producto":
        echo $bdO->consultar("SELECT  * FROM  `precioProductos` WHERE idProducto={$_POST['idProducto']} ORDER BY fecha DESC");
        break;
      case "Precio Productos: Agregar":
        echo $bdO->consultarInsert("INSERT INTO `precioProductos` (fecha, precio, idProducto) VALUES ({$now}, {$_POST['precio']}, {$_POST['idProducto']})");
        break;
      case "Precio Productos: Eliminar":
        echo $bdO->eliminar("DELETE FROM `precioProductos` WHERE idPrecioProducto= {$_POST['idPrecioProducto']}");
        break;
      case "Precio Productos: Actualizar":
        echo $bdO->actualizar("UPDATE `precioProductos` SET precio={$_POST['precio']}  WHERE idPrecioProducto={$_POST['idPrecioProducto']}");
        break;


      case "Citas: Agregar":
        echo $bdO->consultarInsert("INSERT INTO `citas` (idPaciente, fecha, efectuada, idClinica, motivo) VALUES ({$_POST['idPaciente']}, '{$_POST['fecha']}',FALSE, {$_POST['idClinicaDestino']}, '{$_POST['motivo']}')");
        break;
      case "Citas: Finalizar":
        echo $bdO->consultar("UPDATE `citas`SET efectuada=TRUE WHERE idCita = {$_POST['idCita']}");
        break;
      case "Citas: Lista":
        echo $bdO->consultar("SELECT citas.*, pacientes.nombre, pacientes.apellidos FROM `citas`, `pacientes` WHERE citas.fecha > '{$_POST['fecha']}' AND citas.fecha < (DATE('{$_POST['fecha']}') + INTERVAL 1 DAY) AND citas.efectuada=FALSE AND citas.idPaciente=pacientes.idPaciente ORDER BY fecha ASC");
        break;

      case "Citas: Listar":
        echo $bdO->consultar("SELECT citas.*, pacientes.nombre, pacientes.apellidos FROM `citas`, `pacientes` WHERE citas.idClinica={$_POST['idClinicaDestino']} AND citas.fecha > '{$_POST['fecha']}' AND citas.fecha < (DATE('{$_POST['fecha']}') + INTERVAL 1 DAY) AND citas.efectuada=FALSE AND citas.idPaciente=pacientes.idPaciente ORDER BY fecha ASC");
        break;


      case "Citas: Eliminar":
        echo $bdO->consultar("DELETE FROM `citas` WHERE idCita={$_POST['idCita']}");
        break;
      case "Citas: Lista historial":
        echo $bdO->consultar("SELECT citas.*, pacientes.nombre, pacientes.apellidos FROM `citas`, `pacientes` WHERE  citas.efectuada=TRUE AND citas.idPaciente=pacientes.idPaciente AND pacientes.idPaciente = {$_POST['idPaciente']} ORDER BY fecha DESC");
        break;


      case "Reloj: Agregar":
        echo $bdO->consultar("INSERT INTO `relojChecador` (idPersonal, entrada) VALUES ({$_POST['idPersonal']}, '{$_POST['fecha']}')");
        break;
      case "Reloj: Horarios de personal":
        echo $bdO->consultar("SELECT horarios.*, personal.nombre, personal.apellidos FROM horarios, personal WHERE personal.idPersonal = horarios.idPersonal AND personal.idClinica={$_POST['idClinica']}" );
        break;
      case "Reloj: Agregar horarios de personal":
        echo $bdO->consultar("INSERT INTO horarios(lunes, martes, miercoles, jueves, viernes, sabado, domingo, toleranciaEntrada, idPersonal) VALUES('{$_POST['Lunes']}','{$_POST['Martes']}','{$_POST['Miercoles']}','{$_POST['Jueves']}','{$_POST['Viernes']}','{$_POST['Sabado']}','{$_POST['Domingo']}',{$_POST['Tolerancia']},{$_POST['idPersonal']} )" );
        break;
      case "Reloj: Eliminar horarios de personal":
        echo $bdO->consultar("DELETE  FROM `horarios` WHERE horarios.idHorarios=".$_POST["idHorarios"]);
        break;
      case "Reloj: Actualizar horarios de personal":
        echo $bdO->consultar("UPDATE `horarios` SET lunes = '{$_POST['Lunes']}', martes = '{$_POST['Martes']}', miercoles = '{$_POST['Miercoles']}', jueves = '{$_POST['Jueves']}', viernes = '{$_POST['Viernes']}', sabado = '{$_POST['Sabado']}', domingo = '{$_POST['Domingo']}', toleranciaEntrada = {$_POST['Tolerancia']}, idPersonal = {$_POST['idPersonal']} WHERE idHorarios = {$_POST['idHorarios']}" );
        break;

      case "Historial: Listar":
        echo $bdO->consultar("SELECT * FROM `historial` WHERE idPaciente = {$_POST['idPaciente']} ORDER BY idHistorial DESC ");
        break;
      case "Historial: Agregar":
        echo $bdO->consultar("INSERT INTO `historial` (fecha, descripcion, idPaciente) VALUES ('{$_POST['fecha']}', '{$_POST['descripcion']}', {$_POST['idPaciente']})");
        break;
      case "Historial: Eliminar":
        echo $bdO->consultar("DELETE FROM `historial` WHERE  fecha = '{$_POST['fecha']}' AND idPaciente = {$_POST['idPaciente']}");
        break;
      case "Historial: Actualizar":
        echo $bdO->consultar("UPDATE `historial` SET descripcion =  '{$_POST['descripcion']}' WHERE  fecha = '{$_POST['fecha']}' AND idPaciente = {$_POST['idPaciente']}");
        break;

      case "Foto: Listar":
        echo $bdO->consultar("SELECT * FROM `fotos` WHERE idHistorial = {$_POST['idHistorial']} ORDER BY idFoto DESC");
        break;
      case "Foto: Agregar":
        echo $bdO->consultar("INSERT INTO `fotos` (archivo, idHistorial) VALUES ('{$_POST['archivo']}', {$_POST['idHistorial']}) ");
        break;

      case "Tratamientos recetados: Agregar":
        echo $bdO->consultar("INSERT INTO `tratamientosRecetados` (sesiones, sesionesTotales, caducidad, costo, idTratamiento, idHistorial) VALUES (0, {$_POST['sesiones']}, '{$_POST['caducidad']}', '{$_POST['costo']}', {$_POST['idTratamiento']}, {$_POST['idHistorial']})");
        break;
      case "Tratamientos recetados: Listar":
        echo $bdO->consultar("SELECT tratamientosRecetados.*, tratamientos.nombre FROM `tratamientosRecetados`, `tratamientos` WHERE tratamientosRecetados.idTratamiento = tratamientos.idTratamiento AND idHistorial = {$_POST['idHistorial']}");
        break;
      case "Tratamientos recetados: Actualizar":
        $bdO->consultar("UPDATE tratamientosRecetados SET sesiones={$_POST['sesiones']} WHERE tratamientosRecetados.idTratamientoRecetado = {$_POST['idTratamientoRecetado']}");
        echo $bdO->consultar("INSERT INTO `cobrosPendientes` (cantidad, fecha, idTratamientoRecetado) VALUES ({$_POST['cantidad']}, {$now}, {$_POST['idTratamientoRecetado']})");
        break;

      case "Tratamientos recetados: Pagos pendientes":
        echo $bdO->consultar("SELECT pendiente.idTratamientoRecetado, (pendiente.cantidadPendiente-pagado.cantidadPagado) as cantidad2 FROM (SELECT SUM(cantidad) AS cantidadPendiente, idTratamientoRecetado FROM cobrosPendientes GROUP BY idTratamientoRecetado) AS pendiente, (SELECT SUM(cantidad) AS cantidadPagado, idTratamientoRecetado FROM pagosTratamientos GROUP BY idTratamientoRecetado) AS pagado WHERE pendiente.idTratamientoRecetado = pagado.idTratamientoRecetado AND (pendiente.cantidadPendiente-pagado.cantidadPagado) > 0");
        break;

      case "Tratamientos recetados: Pagos pendientes con datos":
        echo $bdO->consultar("SELECT deuda.*, pacientes.* FROM tratamientosRecetados, historial, pacientes, (SELECT pendiente.idTratamientoRecetado, (pendiente.cantidadPendiente-pagado.cantidadPagado) as cantidad FROM (SELECT SUM(cantidad) AS cantidadPendiente, idTratamientoRecetado FROM cobrosPendientes GROUP BY idTratamientoRecetado) AS pendiente, (SELECT SUM(cantidad) AS cantidadPagado, idTratamientoRecetado FROM pagosTratamientos GROUP BY idTratamientoRecetado) AS pagado WHERE pendiente.idTratamientoRecetado = pagado.idTratamientoRecetado AND (pendiente.cantidadPendiente-pagado.cantidadPagado) > 0) AS deuda WHERE deuda.idTratamientoRecetado = tratamientosRecetados.idTratamientoRecetado AND tratamientosRecetados.idHistorial = historial.idHistorial AND historial.idPaciente = pacientes.idPaciente");
        break;

      case "Tratamientos recetados: Pagos realizados":
        echo $bdO->consultar("SELECT * from `pagosTratamientos` WHERE idTratamientoRecetado = {$_POST['idTratamientoRecetado']} ORDER BY fecha DESC");
        break;
      case "Tratamientos recetados: Realizar pago":
        echo $bdO->consultar("INSERT INTO  `pagosTratamientos` (cantidad, fecha, idTratamientoRecetado) VALUES ({$_POST['cantidad']}, {$now}, {$_POST['idTratamientoRecetado']})");
        break;
      case "Tratamientos recetados: Eliminar pago":
        echo $bdO->consultar("DELETE FROM  `pagosTratamientos` WHERE idPagoTratamiento={$_POST['idPagoTratamiento']}");
        break;

      case "Venta Productos: Agregar":
        //echo $bdO->consultarInsert("INSERT INTO `ventasProductos`(fecha, cantidadProductos, subtotal, iva, total, idPersonal, idPaciente, tipoPago, idPersonalComision, idClinica) VALUES ({$now}, {$_POST['cantidadProductos']}, {$_POST['subtotal']}, {$_POST['iva']}, {$_POST['total']}, {$_POST['idPersonal']}, {$_POST['idPaciente']}, {$_POST['tipoPago']}, {$_POST['idPersonalComision']}, {$_POST['idClinica']})");
        echo $bdO->consultarInsert("INSERT INTO `ventasProductos`(fecha, cantidadProductos, subtotal, iva, total, idPersonal, idPaciente, tipoPago, idClinica, descuento) VALUES ({$now}, {$_POST['cantidadProductos']}, {$_POST['subtotal']}, {$_POST['iva']}, {$_POST['total']}, {$_POST['idPersonalComision']}, {$_POST['idPaciente']}, {$_POST['tipoPago']},  {$_POST['idClinica']}, {$_POST['descuento']})");
        break;
      case "Venta Productos: Agregar detalles":
        $bdO->consultar("INSERT INTO `ventasProductosDetalles`(cantidad, costo, total, idProducto, idVentasProductos) VALUES ({$_POST['cantidad']}, {$_POST['costo']}, {$_POST['total']}, {$_POST['idProducto']}, {$_POST['idVentasProductos']})");
        echo $bdO->consultar("INSERT INTO `almacen` (cantidad, fecha, autorizado, idProducto) VALUES (-{$_POST['cantidad']}, {$now}, TRUE, {$_POST['idProducto']})");
        break;
      case "Venta Productos: Listar":
        echo $bdO->consultar("SELECT * FROM ventasProductos WHERE ventasProductos.idClinica={$_POST['idClinica']} AND fecha >= DATE({$now}) - INTERVAL 7 DAY ORDER BY fecha DESC");
        break;
      case "Venta Productos: Listar detalles":
        echo $bdO->consultar("SELECT productos.idProducto, ventasProductosDetalles.idVentaProductosDetalle, ventasProductosDetalles.cantidad, productos.nombre, ventasProductosDetalles.costo, ventasProductosDetalles.total FROM productos, ventasProductosDetalles, ventasProductos WHERE productos.idProducto = ventasProductosDetalles.idProducto AND ventasProductosDetalles.idVentasProductos = ventasProductos.idVentaProductos AND ventasProductos.idVentaProductos={$_POST['idVentaProductos']}");
        break;
      case "Venta Productos: Eliminar":
        $bdO->consultar("DELETE FROM ventasProductosDetalles WHERE idVentasProductos={$_POST['idVentaProductos']}");
        $bdO->consultar("DELETE FROM ventasCobros WHERE idVentaProductos={$_POST['idVentaProductos']}");
        echo $bdO->consultar("DELETE FROM ventasProductos WHERE idVentaProductos={$_POST['idVentaProductos']}");
        break;
      case "Venta Productos: Eliminar fila":
        $bdO->consultar("UPDATE ventasProductos set cantidadProductos={$_POST['cantidadProductos']}, subtotal={$_POST['subtotal']}, total={$_POST['total']} WHERE idVentaProductos={$_POST['idVentaProductos']}");
        echo $bdO->consultar("DELETE FROM ventasProductosDetalles WHERE idVentaProductosDetalle={$_POST['idVentaProductosDetalle']}");
        break;


      case "Venta Productos: Editar Venta":
        echo $bdO->consultar("UPDATE ventasProductos set cantidadProductos={$_POST['cantidadProductos']}, total={$_POST['total']} WHERE idVentaProductos={$_POST['idVentaProductos']}");
        break;
      case "Venta Productos: Editar Venta Detalles":
        echo $bdO->consultar("UPDATE ventasProductosDetalles set cantidad={$_POST['cantidad']} WHERE idVentaProductosDetalle={$_POST['idVentaProductosDetalle']}");
        break;

      case "Reportes: Existencia en almacen":
      //echo $bdO->consultar("SELECT productos.nombre AS Producto, productos.cantidadMinima as CantidadMinima, vistaAlmacen.existencia as Existencia FROM tiposProductos, productos, (SELECT SUM(cantidad) AS existencia, idProducto FROM almacen GROUP BY idProductod WHERE idClinica={$_POST['idClinica']}) AS vistaAlmacen WHERE productos.idProducto =vistaAlmacen.idProducto AND productos.idTipoProducto=tiposProductos.idTipoProducto AND tiposProductos.idClinica={$_POST['idClinica']} ORDER BY vistaAlmacen.existencia ASC");
        echo $bdO->consultar("SELECT productos.nombre AS Producto, productos.cantidadMinima as CantidadMinima, vistaAlmacen.existencia as Existencia FROM tiposProductos, productos, (SELECT SUM(cantidad) AS existencia, idProducto FROM almacen WHERE idClinica={$_POST['idClinica']} GROUP BY idProducto ) AS vistaAlmacen WHERE productos.idProducto =vistaAlmacen.idProducto AND productos.idTipoProducto=tiposProductos.idTipoProducto ORDER BY vistaAlmacen.existencia ASC");
        break;





      case "Caja: Lista":
        echo $bdO->consultar("SELECT * FROM `caja` WHERE idClinica = {$_POST['idClinica']} ORDER BY nombre ASC");
        break;
      case "Caja: Agregar":
        echo $bdO->consultar("INSERT INTO `caja` (nombre, descripcion, impresoraTicket, impresoraReporte, idClinica) VALUES ('{$_POST['nombre']}', '{$_POST['descripcion']}', '{$_POST['impresoraTicket']}', '{$_POST['impresoraReporte']}', {$_POST['idClinica']})");
        break;
      case "Caja: Eliminar":
        echo $bdO->consultar("DELETE FROM `caja` WHERE  idCaja = {$_POST['idCaja']}");
        break;
      case "Caja: Actualizar":
        echo $bdO->consultar("UPDATE `caja` SET nombre =  '{$_POST['nombre']}', descripcion =  '{$_POST['descripcion']}', impresoraTicket =  '{$_POST['impresoraTicket']}', impresoraReporte =  '{$_POST['impresoraReporte']}' WHERE idCaja = {$_POST['idCaja']}");
        break;


      case "CajaCorte: Insertar":
        echo $bdO->consultar("INSERT INTO `cajaCorte` (idCaja, idPersonal, monto, tipo, fecha, hora, fechaCompleta, idPersonalAut) VALUES ({$_POST['idCaja']}, {$_POST['idPersonal']}, {$_POST['monto']}, {$_POST['tipo']}, {$now}, {$now}, {$now}, {$_POST['idPersonalAut']} )");
        break;
      case "CajaCorte: Cerrar":
        //echo $bdO->consultar("SELECT SUM(ventasProductos.total) AS venta FROM `ventasProductos` WHERE ventasProductos.fecha>  (SELECT fechaCompleta FROM `cajaCorte` WHERE idCaja={$_POST['idCaja']} ORDER BY fechaCompleta DESC LIMIT 0, 1)");
/*
        echo $bdO->consultar("SELECT * FROM (SELECT SUM(ventasProductos.total) AS vTotal FROM `ventasProductos` WHERE ventasProductos.idPersonal={$_POST['idPersonal']} AND ventasProductos.fecha>  (SELECT fechaCompleta FROM `cajaCorte` WHERE idCaja={$_POST['idCaja']} ORDER BY fechaCompleta DESC LIMIT 0, 1)) as c1, (SELECT monto-cambio as vTarjeta FROM (SELECT sum(ventasCobros.monto) as monto FROM `ventasProductos`, ventasCobros WHERE ventasProductos.idPersonal={$_POST['idPersonal']} AND ventasProductos.fecha>  (SELECT fechaCompleta FROM `cajaCorte` WHERE idCaja={$_POST['idCaja']} ORDER BY fechaCompleta DESC LIMIT 0, 1)  AND ventasCobros.idVentaProductos=ventasProductos.idVentaProductos AND ventasCobros.formaPago=1) as t1, (SELECT sum(ventasCobros.monto) as cambio FROM `ventasProductos`, ventasCobros WHERE ventasProductos.idPersonal={$_POST['idPersonal']} AND ventasProductos.fecha>  (SELECT fechaCompleta FROM `cajaCorte` WHERE idCaja={$_POST['idCaja']} ORDER BY fechaCompleta DESC LIMIT 0, 1) AND ventasCobros.idVentaProductos=ventasProductos.idVentaProductos AND ventasCobros.formaPago=3) as t2) as c2");
*/

        echo $bdO->consultar("SELECT SUM(vTotal) as vTotal, SUM(vTarjeta) as vTarjeta FROM (SELECT ventasProductosDetalles.idVentasProductos, sum(ventasProductosDetalles.total) as vTotal FROM ventasProductosDetalles, ventasProductos WHERE ventasProductos.idClinica={$_POST['idClinica']} AND ventasProductos.idPersonal={$_POST['idPersonal']} AND ventasProductosDetalles.idVentasProductos=ventasProductos.idVentaProductos AND ventasProductos.fecha> (SELECT fechaCompleta FROM `cajaCorte` WHERE idCaja={$_POST['idCaja']} AND idPersonal={$_POST['idPersonal']} ORDER BY fechaCompleta DESC LIMIT 0, 1) group by idVentasProductos) s1 LEFT JOIN (SELECT idVentaProductos, SUM(monto) as vTarjeta FROM ventasCobros WHERE ventasCobros.formaPago=2 group BY ventasCobros.idVentaProductos) s2 ON s1.idVentasProductos = s2.idVentaProductos");
        break;
      case "CajaCorte: Listar":
        echo $bdO->consultar("SELECT cajaCorte.*, caja.impresoraTicket, caja.impresoraReporte FROM cajaCorte, caja WHERE idPersonal = {$_POST['idPersonal']} AND caja.idCaja = cajaCorte.idCaja AND caja.idClinica = {$_POST['idClinica']} ORDER BY idCajaCorte DESC LIMIT 1");
        break;

      case "Reporte: General":
        echo $bdO->consultar("SELECT ventasProductos.idVentaProductos, personal.idPersonal, personal.idPersonal as Id, CONCAT(personal.nombre,' ', personal.apellidos) AS Vendedor, SUM(ventasProductos.total) AS Ventas FROM ventasProductos, personal WHERE ventasProductos.idClinica={$_POST['idClinica']} AND personal.idPersonal = ventasProductos.idPersonal AND  fecha BETWEEN '{$_POST['fechaInicial']}' AND  '{$_POST['fechaFinal']} 23:59' GROUP BY ventasProductos.idPersonal ORDER BY personal.nombre");
        break;

      case "Reporte: Personal":
        //echo $bdO->consultar("SELECT DATE_FORMAT(CONVERT_TZ(ventasProductos.fecha,'+00:00','-05:00'), '%Y/%m/%d %k:%i') AS Fecha, ventasProductos.idVentaProductos AS IdVenta, ventasProductos.cantidadProductos AS Productos, ventasProductos.total AS Total, IF(ventasProductos.idPaciente=-1, 'Mostrador', CONCAT(pacientes.nombre,' ', pacientes.apellidos))  AS Cliente  FROM ventasProductos, personal, pacientes WHERE pacientes.idPaciente=ventasProductos.idPaciente AND  personal.idPersonal = ventasProductos.idPersonal AND  fecha BETWEEN '{$_POST['fechaInicial']}' AND '{$_POST['fechaFinal']} 23:59'  AND personal.idPersonal = {$_POST['idPersonal']} ORDER BY ventasProductos.fecha DESC");

        echo $bdO->consultar("SELECT ventasProductos.fecha AS Fecha, ventasProductos.idVentaProductos AS IdVenta, ventasProductos.cantidadProductos AS Productos, ventasProductos.total AS Total, IF(ventasProductos.idPaciente=-1, 'Mostrador', CONCAT(pacientes.nombre,' ', pacientes.apellidos))  AS Cliente  FROM ventasProductos, personal, pacientes WHERE ventasProductos.idClinica={$_POST['idClinica']} AND pacientes.idPaciente=ventasProductos.idPaciente AND  personal.idPersonal = ventasProductos.idPersonal AND  fecha BETWEEN '{$_POST['fechaInicial']}' AND '{$_POST['fechaFinal']} 23:59'  AND personal.idPersonal = {$_POST['idPersonal']} ORDER BY ventasProductos.fecha DESC");
        break;

      case "Reporte: General completo":
        echo $bdO->consultar("SELECT * FROM (SELECT ventasProductos.fecha, ventasProductos.idVentaProductos as IdVenta, CONCAT(pacientes.nombre,' ', pacientes.apellidos) AS Paciente, ventasProductos.total AS Total FROM ventasProductos, pacientes WHERE ventasProductos.idClinica={$_POST['idClinica']}  AND  pacientes.idPaciente = ventasProductos.idPaciente GROUP BY idVentaProductos) AS p  LEFT JOIN (SELECT idVentaProductos, SUM(monto) AS Efectivo FROM ventasCobros WHERE formaPago=1 GROUP BY idVentaProductos) Efectivo ON Efectivo.idVentaProductos = p.IdVenta LEFT JOIN (SELECT idVentaProductos, SUM(monto) AS Tarjeta FROM ventasCobros WHERE formaPago=2 GROUP BY idVentaProductos) Tarjeta ON Tarjeta.idVentaProductos = p.idVenta LEFT JOIN (SELECT ventasProductosDetalles.idVentasProductos, SUM(total) AS Producto FROM ventasProductosDetalles, productos WHERE ventasProductosDetalles.idProducto = productos.idProducto AND productos.cantidadMinima>-1 GROUP BY ventasProductosDetalles.idVentasProductos) Producto ON Producto.idVentasProductos = p.idVenta LEFT JOIN (SELECT ventasProductosDetalles.idVentasProductos, SUM(total) AS Tratamiento FROM ventasProductosDetalles, productos WHERE ventasProductosDetalles.idProducto = productos.idProducto AND productos.cantidadMinima=-1 GROUP BY ventasProductosDetalles.idVentasProductos) Tratamiento ON Tratamiento.idVentasProductos = p.idVenta WHERE  fecha BETWEEN '{$_POST['fechaInicial']}' AND  '{$_POST['fechaFinal']} 23:59' ");
        break;
      case "Reportes: Existencia en almacen verificar":
      //echo $bdO->consultar("SELECT productos.nombre AS Producto, productos.cantidadMinima as CantidadMinima, vistaAlmacen.existencia as Existencia FROM tiposProductos, productos, (SELECT SUM(cantidad) AS existencia, idProducto FROM almacen GROUP BY idProductod WHERE idClinica={$_POST['idClinica']}) AS vistaAlmacen WHERE productos.idProducto =vistaAlmacen.idProducto AND productos.idTipoProducto=tiposProductos.idTipoProducto AND tiposProductos.idClinica={$_POST['idClinica']} ORDER BY vistaAlmacen.existencia ASC");
        echo $bdO->consultar("SELECT * FROM (SELECT productos.idProducto, productos.nombre AS Producto, productos.clave AS Clave, tiposProductos.nombre AS Clase from productos, tiposProductos WHERE productos.idTipoProducto= tiposProductos.idTipoProducto ) Prod LEFT JOIN (SELECT almacen.idProducto, SUM(almacen.cantidad) AS Existencia FROM almacen WHERE almacen.idClinica={$_POST['idClinica']} GROUP BY almacen.idProducto) Alma ON Prod.idProducto = Alma.idProducto ORDER BY clave ASC");
        break;

      case "Cobros de venta: Agregar":
        echo $bdO->consultar("INSERT INTO ventasCobros (idVentaProductos, formaPago, monto, descripcion) VALUES ({$_POST['idVentaProductos']}, {$_POST['formaPago']}, {$_POST['monto']}, '{$_POST['descripcion']}' )");
        break;

      case "Cobros de venta: listar":
        echo $bdO->consultar("SELECT * FROM ventasCobros WHERE idVentaProductos = {$_POST['idVentaProductos']}");
        break;

      case "Version: listar":
        echo $bdO->consultar("SELECT * FROM versiones ORDER BY idVersion DESC LIMIT 1");
        break;

      case "Descuentos: Listar":
        echo $bdO->consultar("SELECT * FROM `descuentos` ORDER BY cantidad ASC");
        break;

      case "Descuentos: Agregar":
        echo $bdO->consultar("INSERT INTO `descuentos` (cantidad, descripcion, idClinica) VALUES ({$_POST['cantidad']}, '{$_POST['descripcion']}',  {$_POST['idClinica']})");
        break;
      case "Descuentos: Eliminar":
        echo $bdO->consultar("DELETE FROM `descuentos` WHERE  idDescuento = {$_POST['idDescuento']}");
        break;
      case "Descuentos: Actualizar":
        echo $bdO->consultar("UPDATE `descuentos` SET cantidad =  {$_POST['cantidad']}, descripcion =  '{$_POST['descripcion']}' WHERE idDescuento = {$_POST['idDescuento']}");
        break;


    }
  }
  else {
    echo "conexion FAIL";
  }
 ?>
