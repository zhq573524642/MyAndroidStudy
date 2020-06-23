#include "com_zhq_exclusivememory_ui_activity_jni_JniTest.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_com_zhq_exclusivememory_ui_activity_jni_JniTest_get(JNIEnv *env,jobject thiz){
   printf("invoke get from C\n");
   return (*env)->NewStringUTF(env,"Hello from JNI !");
}

JNIEXPORT void JNICALL Java_com_zhq_exclusivememory_ui_activity_jni_JniTest_set(JNIEnv *env,jobject thiz,jstring string){
    printf("invoke set from C\n");
    char* str=(char*)(*env)->GetStringUTFChars(env,string ,NULL);
    printf("%s\n",str);
    (*env)->ReleaseStringUTFChars(env,string,str);
}