/*
*  MyFirstOpenCVJNI.java
*/

package my.project.MyFirstOpenCVJNI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

public class MyFirstOpenCVJNI extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
		InputStream is;
		is = this.getResources().openRawResource(R.drawable.foot);
		Bitmap bmInImg = BitmapFactory.decodeStream(is);

		int [] mPhotoIntArray;
		int [] mCannyOutArray;
        
		mPhotoIntArray = new int[bmInImg.getWidth() * bmInImg.getHeight()];
		// Copy pixel data from the Bitmap into the 'intArray' array
		bmInImg.getPixels(mPhotoIntArray, 0, bmInImg.getWidth(), 0, 0, bmInImg.getWidth(), bmInImg.getHeight());
        
		//create the canny result buffer
		mCannyOutArray = new int[bmInImg.getWidth() * bmInImg.getHeight()];
		
		//
		// Do Canny 
		//
		CannyJNI(bmInImg.getHeight(), bmInImg.getWidth(), mPhotoIntArray, mCannyOutArray);
		
		//
		// Convert the result to Bitmap
		//
		Bitmap bmOutImg = Bitmap.createBitmap(bmInImg.getWidth(), bmInImg.getHeight(), Config.ARGB_8888);  
		bmOutImg.setPixels(mCannyOutArray, 0, bmInImg.getWidth(), 0, 0, bmInImg.getWidth(), bmInImg.getHeight());
		
		//
		// Save the result to file
		//
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		String outFileName = extStorageDirectory + "/outCanny.png";
    	
    	OutputBitmapToFile(bmOutImg, outFileName);
		
		
    }
    
    void OutputBitmapToFile(Bitmap InBm, String Filename)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		InBm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
	
		File f = new File(Filename);
		try
		{
			f.createNewFile();
			//write the bytes in file
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}			
	}
    
    
	public native boolean CannyJNI(int width, int height, int [] mPhotoIntArray, int [] mCannyOutArray);
	
    static 
    {
        System.loadLibrary("first-opencvjni");
    }
    
    
}