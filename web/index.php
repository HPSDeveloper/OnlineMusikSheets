<?php
echo "<a href='sets'>Song sets ...</a><br /><br />";
$path = ".";
$files = scandir($path, 0);
foreach ($files as &$file) {
    if($file != "." && $file != ".." && $file != "index.php" && $file != ".htaccess" && $file != "error_log" && $file != "cgi-bin") {
        $info = pathinfo($file);
        if ($info["extension"] == "html") {
            /* echo "<a href='$path/$file'>$file</a><a> - </a>"; */
           echo "<a href='$path/$file?referer=index.php'>$file</a><br /><br />";
        }
        $i++;
    }
}
?>