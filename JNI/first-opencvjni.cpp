/*
*  first-opencvjni.cpp
*/

#include <jni.h>

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc_c.h>

using namespace cv;

extern "C"
jboolean
Java_my_project_MyFirstOpenCVJNI_MyFirstOpenCVJNI_CannyJNI( 
		JNIEnv* env, jobject thiz, 
		jint height, jint width, jintArray in, jintArray out)
{
	//get the data pointer.
	jint* _in = env->GetIntArrayElements(in, 0);
    jint* _out = env->GetIntArrayElements(out, 0);


	//Build the Mat structure for input data
	Mat mSrc(height, width, CV_8UC4, (unsigned char *)_in);
	//Build the Mat structure for output data
	Mat mOut(height, width, CV_8UC4, (unsigned char *)_out);

	//Convert Mat to IplImage
	IplImage mSrcImg = mSrc;
	IplImage mOutImg = mOut;

	//Create the gray image for input data.
	IplImage * mSrcGrayImg = cvCreateImage(cvGetSize(&mSrcImg), mSrcImg.depth, 1);
	IplImage * mOutGrayImg = cvCreateImage(cvGetSize(&mSrcImg), mSrcImg.depth, 1);

	//Convert to Gray image
	cvCvtColor(&mSrcImg, mSrcGrayImg, CV_BGR2GRAY);

	//Do canny
	cvCanny(mSrcGrayImg, mOutGrayImg, 80, 100, 3);

	//Convert Gray image to bitmap BGR
	cvCvtColor(mOutGrayImg, &mOutImg, CV_GRAY2BGR);

	//release the pointer. 
    env->ReleaseIntArrayElements(in, _in, 0);
    env->ReleaseIntArrayElements(out, _out, 0);
	return true;
}

