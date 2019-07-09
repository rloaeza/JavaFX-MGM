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
    $src = 'fotos/'.$_POST['nombreFoto'];
    $dest ='fotos/_'.$_POST['nombreFoto'];
    $desired_width="200";
    move_uploaded_file ($_FILES['userfile'] ['tmp_name'],  $src);
    make_thumb($src, $dest, $desired_width);

  }
?>
