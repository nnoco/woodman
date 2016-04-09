package com.nnoco.data;

public class FileShare {
/*
 안녕하세요. 제가 내외부 메모리에서 이미지파일을 저장해서 공유하는 기능을 구현중인데요. 
제대로 구현한것인지 알고자 글을 올립니다. 공유부분인데 설정을 잘못한건지 공유가 되는 어플이 있고 안되는 어플이 있네요..ㅜㅜ 

우선 외부메모리(sdcard)에서 파일 저장하고 공유한 부분입니다.
 externalDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"temp");
 externalDir.mkdir();
요렇게 우선 디렉토리를 생성하였습니다.

그다음에 tempBitmap라는 비트맵파일을 미리 만들어 놓고
FileOutputStream out = new FileOutputStream("/sdcard/temp/temp.jpg");
tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
요런식으로 temp디렉토리에 있는 temp.jpg에 저장을 하였구요.

다음으로는 공유하는 부분입니다.
 Intent intent = new Intent(Intent.ACTION_SEND);
 File file = new File("/sdcard/roughmap/temp.jpg"); 
 Uri uri =Uri.fromFile(file);
 intent.setType("image/jpg");
 intent.putExtra(Intent.EXTRA_STREAM,uri );
 startActivity(Intent.createChooser(intent, "Choose"));
이렇게 해서 sdcard에 이미지를 저장한 후 공유하는 것을 구현하였습니다.

다음은 내부메모리에서 파일을 저장하고 공유한 부분입니다.
따로 디렉토리 설정안하고 기본(files)디렉토리밑에 저장하였습니다.
위와 마찬가지로 tempBitmap이라는 비트맵을 만들어서 내부메모리에 temp.jpg라는 파일에 저장하는 부분입니다.
FileOutputStream internalOut = getContext().openFileOutput("temp.jpg",Context.MODE_WORLD_READABLE);
tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, internalOut);

다음으로 공유하는 부분입니다.
 File file = new File("/data/data/설치된 패키지 이름/files/temp.jpg")  ;
 Uri uri =Uri.fromFile(file);
intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
intent.setType("image/jpg");
intent.putExtra(Intent.EXTRA_STREAM,uri );
startActivity(Intent.createChooser(intent, "Choose"));
이렇게 해서 내부메모리에 이미지를 저장한 후 공유하는 것을 구현하였습니다. 빨간부분이 sdcard부분과 다른 부분입니다.
 
 
 
 2번 : 안드로이드에서 어플간 또는 어플내 연동방식에 대한 내용입니다만,  
Intent intent = new Intent(Intent.ACTION_SEND); ....; startActivity(intent);
이렇게 되면, 이게 무슨 소리냐 하면, "시스템에 등록된 모든 액티비티 중에, ACTION_SEND 기능이 있는 액티비티 나와라.. 오바!!"
하고 안드로이드 시스템에 주문을 넣는 것과 같습니다. 이 때 자동 혹은 수동으로 선택된 액티비티로 모든 제어가 넘어가버립니다.
일단 제어가 넘어가면 그 때부터는 선택되어 실행된 액티비티가 알아서 동작하는 거죠. 호출자가 더이상 손쓸 방법은 없다고 보면 될거 같습니다.
 */
}
