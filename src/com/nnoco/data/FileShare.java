package com.nnoco.data;

public class FileShare {
/*
 �ȳ��ϼ���. ���� ���ܺ� �޸𸮿��� �̹��������� �����ؼ� �����ϴ� ����� �������ε���. 
����� �����Ѱ����� �˰��� ���� �ø��ϴ�. �����κ��ε� ������ �߸��Ѱ��� ������ �Ǵ� ������ �ְ� �ȵǴ� ������ �ֳ׿�..�̤� 

�켱 �ܺθ޸�(sdcard)���� ���� �����ϰ� ������ �κ��Դϴ�.
 externalDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"temp");
 externalDir.mkdir();
�䷸�� �켱 ���丮�� �����Ͽ����ϴ�.

�״����� tempBitmap��� ��Ʈ�������� �̸� ����� ����
FileOutputStream out = new FileOutputStream("/sdcard/temp/temp.jpg");
tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
�䷱������ temp���丮�� �ִ� temp.jpg�� ������ �Ͽ�����.

�������δ� �����ϴ� �κ��Դϴ�.
 Intent intent = new Intent(Intent.ACTION_SEND);
 File file = new File("/sdcard/roughmap/temp.jpg"); 
 Uri uri =Uri.fromFile(file);
 intent.setType("image/jpg");
 intent.putExtra(Intent.EXTRA_STREAM,uri );
 startActivity(Intent.createChooser(intent, "Choose"));
�̷��� �ؼ� sdcard�� �̹����� ������ �� �����ϴ� ���� �����Ͽ����ϴ�.

������ ���θ޸𸮿��� ������ �����ϰ� ������ �κ��Դϴ�.
���� ���丮 �������ϰ� �⺻(files)���丮�ؿ� �����Ͽ����ϴ�.
���� ���������� tempBitmap�̶�� ��Ʈ���� ���� ���θ޸𸮿� temp.jpg��� ���Ͽ� �����ϴ� �κ��Դϴ�.
FileOutputStream internalOut = getContext().openFileOutput("temp.jpg",Context.MODE_WORLD_READABLE);
tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, internalOut);

�������� �����ϴ� �κ��Դϴ�.
 File file = new File("/data/data/��ġ�� ��Ű�� �̸�/files/temp.jpg")  ;
 Uri uri =Uri.fromFile(file);
intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
intent.setType("image/jpg");
intent.putExtra(Intent.EXTRA_STREAM,uri );
startActivity(Intent.createChooser(intent, "Choose"));
�̷��� �ؼ� ���θ޸𸮿� �̹����� ������ �� �����ϴ� ���� �����Ͽ����ϴ�. �����κ��� sdcard�κа� �ٸ� �κ��Դϴ�.
 
 
 
 2�� : �ȵ���̵忡�� ���ð� �Ǵ� ���ó� ������Ŀ� ���� �����Դϴٸ�,  
Intent intent = new Intent(Intent.ACTION_SEND); ....; startActivity(intent);
�̷��� �Ǹ�, �̰� ���� �Ҹ��� �ϸ�, "�ý��ۿ� ��ϵ� ��� ��Ƽ��Ƽ �߿�, ACTION_SEND ����� �ִ� ��Ƽ��Ƽ ���Ͷ�.. ����!!"
�ϰ� �ȵ���̵� �ý��ۿ� �ֹ��� �ִ� �Ͱ� �����ϴ�. �� �� �ڵ� Ȥ�� �������� ���õ� ��Ƽ��Ƽ�� ��� ��� �Ѿ�����ϴ�.
�ϴ� ��� �Ѿ�� �� �����ʹ� ���õǾ� ����� ��Ƽ��Ƽ�� �˾Ƽ� �����ϴ� ����. ȣ���ڰ� ���̻� �վ� ����� ���ٰ� ���� �ɰ� �����ϴ�.
 */
}
