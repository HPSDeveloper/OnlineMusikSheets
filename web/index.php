<?php
echo "<!DOCTYPE html>";
echo "<html lang='en'>";
echo "<head>";
echo "    <meta charset='UTF-8'>";
echo "    <title>Alle Lieder</title>";
echo "</head>";
echo "<body>";
echo "<a href='sets'>Song sets ...</a><br /><br />";

$umlaute = array("/Ä/","/ä/","/Ö/","/ö/","/Ü/","/ü/");
$replace = array("%c4","%e4","%d6","%f6","%dc","%fc");

$path = ".";
$files = scandir($path, 0);
foreach ($files as &$file) {
    if($file != "." && $file != ".." && $file != "index.php" && $file != ".htaccess" && $file != "error_log" && $file != "cgi-bin") {
        $info = pathinfo($file);
        if ($info["extension"] == "html") {
            $utf8FileName = mb_convert_encoding($file, 'UTF-8', 'ISO-8859-1');
            $displayFileName = htmlentities($utf8FileName, ENT_QUOTES, 'UTF-8');
            $urlFileName = $street_new = preg_replace($umlaute, $replace, $utf8FileName);
           echo "<a href='$path/$urlFileName?referer=index.php'>$displayFileName</a><br /><br />";
        }
        $i++;
    }
}
echo "</body><head>";
?>