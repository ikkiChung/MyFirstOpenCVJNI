#
#  Android.mk
#

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_LIB_TYPE:=STATIC
OPENCV_INSTALL_MODULES:=on

include ../includeOpenCV.mk
include $(OPENCV_MK_PATH)

LOCAL_MODULE    := first-opencvjni
LOCAL_SRC_FILES := first-opencvjni.cpp
LOCAL_LDLIBS +=  -llog -ldl

include $(BUILD_SHARED_LIBRARY)
