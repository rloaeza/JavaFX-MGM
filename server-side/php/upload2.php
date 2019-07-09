<?php
  function make_thumb($src, $dest, $desired_width) {

      /* read the source image */
      $source_image = imagecreatefromjpeg($src);
      $width = imagesx($source_image);
      $height = imagesy($source_image);

      /* find the "desired height" of this thumbnail, relative to the desired width  */
      $desired_height = floor($height * ($desired_width / $width));

      /* create a new, "virtual" image */
      $virtual_image = imagecreatetruecolor($desired_width, $desired_height);

      /* copy source image at a resized size */
      imagecopyresampled($virtual_image, $source_image, 0, 0, 0, 0, $desired_width, $desired_height, $width, $height);

      /* create the physical thumbnail image to its destination */
      imagejpeg($virtual_image, $dest);
  }



  if (is_uploaded_file($_FILES['userfile']['tmp_name'])) {
    $src = $_POST['directorio'].'/'.$_POST['nombreFoto'];
    $dest =$_POST['directorio'].'/_'.$_POST['nombreFoto'];
    $desired_width=$_POST['res'];
    move_uploaded_file ($_FILES['userfile'] ['tmp_name'],  $src);
    make_thumb($src, $dest, $desired_width);

  }
?>
