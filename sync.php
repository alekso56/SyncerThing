<?php
$fi = new FilesystemIterator(__DIR__, FilesystemIterator::SKIP_DOTS);
class sync
{
    public $mp4files = array();
    public $mp4signatures = array();
    public $srt = array();
    public $srtsignatures = array();
    public $sel = 0;
    public $userCount = 0;
}
class users
{
    public $ips = array();
    public $timestamps = array();
}

$e    = new sync();
$userlist = new users();

$ip   = $_SERVER['REMOTE_ADDR'];
$newTimeStamp = time();

$dir  = __DIR__ . "/users.bin";
if (file_exists($dir)) {
    $userlist = json_decode(file_get_contents($dir));
    $c = 0;
    if (!empty($userlist->ips)) {
        foreach ($userlist->ips as $ips) {
            $diff = $userlist->timestamps[$c];
            $diff = $newTimeStamp-$diff;
            if($userlist->ips[$c] !== $ip){
                if($diff > 120){
                    //echo "remove ip; ".$userlist->ips[$c];
                    unset($userlist->timestamps[$c]);
                    unset($userlist->ips[$c]);
                    $userlist->timestamps = array_values($userlist->timestamps);
                    $userlist->ips = array_values($userlist->ips);
                }
            }elseif($userlist->ips[$c] === $ip){
                $userlist->timestamps[$c] = $newTimeStamp;
                //echo "update; ".$userlist->ips[$c];
            }
            $c++;
        }
        $isIn = in_array($ip, $userlist->ips);
        if(!$isIn){
            array_push($userlist->ips, $ip);
            array_push($userlist->timestamps, $newTimeStamp."");
        }
    }else{
        array_push($userlist->ips, $ip);
        array_push($userlist->timestamps, $newTimeStamp."");
    }
    file_put_contents($dir, json_encode($userlist));
} else {
    array_push($userlist->ips, $ip);
    array_push($userlist->timestamps, $newTimeStamp."");
    file_put_contents($dir, json_encode($userlist));
}



foreach ($fi as $file) {
    if (basename($file) !== "sync.php" && basename($file) !== "users.bin") {
        if (pathinfo($file, PATHINFO_EXTENSION) === "mp4") {
            array_push($e->mp4files, basename($file));
            array_push($e->mp4signatures, hash_file('md5', $file));
        } elseif (pathinfo($file, PATHINFO_EXTENSION) === "srt") {
            array_push($e->srt, basename($file));
            array_push($e->srtsignatures, hash_file('md5', $file));
        }
        $e->userCount = count($e->ips)+1;
    }
}
echo (json_encode($e));
?> 