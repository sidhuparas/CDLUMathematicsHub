package com.parassidhu.cdlumaths.utils;

import androidx.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {
    public boolean done;
    public String filename;
    public String url;
    public Integer clickSem1;
    public Integer clickSem2;
    public Integer clickSem3;
    public Integer clickSem4;
    public Integer clickSem5;
    public Integer clickSem6;
    public Integer clickSem7;
    public Integer clickSem8;
    public Integer clickSem9;
    public Integer clickSem10;
    public Integer hitSem1;
    public Integer hitSem2;
    public Integer hitSem3;
    public Integer hitSem4;
    public Integer ID;

    @Override
    public void onCreate(){
        super.onCreate();
        done=false;
    }

    public Integer getID(Integer id){
        ID=id;
        return id;
    }
    public String getFilename(String name){
        filename =name;
        return name;
    }
    public String getUrl(String ur){
        url = ur;
        return ur;
    }
    public Integer getClickSem1(Integer sem){
        clickSem1=sem;
        return sem;
    }
    public Integer getClickSem2(Integer sem){
        clickSem2=sem;
        return sem;
    }
    public Integer getClickSem3(Integer sem){
        clickSem3=sem;
        return sem;
    }
    public Integer getClickSem4(Integer sem){
        clickSem4=sem;
        return sem;
    }
    public Integer getClickSem5(Integer sem){
        clickSem5=sem;
        return sem;
    }
    public Integer getClickSem6(Integer sem){
        clickSem6=sem;
        return sem;
    }
    public Integer getClickSem7(Integer sem){
        clickSem7=sem;
        return sem;
    }
    public Integer getClickSem8(Integer sem){
        clickSem8=sem;
        return sem;
    }
    public Integer getClickSem9(Integer sem){
        clickSem9=sem;
        return sem;
    }
    public Integer getClickSem10(Integer sem){
        clickSem10=sem;
        return sem;
    }
    public Integer getHitSem1(Integer sem){
        hitSem1=sem;
        return sem;
    }
    public Integer getHitSem2(Integer sem){
        hitSem2=sem;
        return sem;
    }
    public Integer getHitSem3(Integer sem){
        hitSem3=sem;
        return sem;
    }
    public Integer getHitSem4(Integer sem){
        hitSem4=sem;
        return sem;
    }
    public Integer getit1(){
        return clickSem1;
    }
    public Integer getit2(){
        return clickSem2;
    }
    public Integer getit3(){
        return clickSem3;
    }
    public Integer getit4(){
        return clickSem4;
    }
    public Integer getit5() {return clickSem5;}
    public Integer getit6() {return clickSem6;}
    public Integer getit7() {return clickSem7;}
    public Integer getit8() {return clickSem8;}
    public Integer getit9() {return clickSem9;}
    public Integer getit10() {return clickSem10;}
    public Integer hitit1() {return hitSem1;}
    public Integer hitit2() {return hitSem2;}
    public Integer hitit3() {return hitSem3;}
    public Integer hitit4() {return hitSem4;}
}
