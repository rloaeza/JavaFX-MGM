<?php

  $dir = "fotos/".$_GET['foto'];

  header('Content-type: image/jpeg');

  $create = imagecreatetruecolor(150, 150); 
  $img = imagecreatefromjpeg($dir);
  list($width, $height) = getimagesize($dir);
  imagecopyresampled($create, $img, 0, 0, 0, 0, 150, 150, $width, $height);

  imagejpeg($create, null, 100);
?>
