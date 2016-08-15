package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

/**
 * Created by musz on 7/25/2016.
 */
public class ConstantUrl {

    private String urlJSONuser = "http://service.eternity.co.th/tires/system/CenterService/getUser.php";
    private String urlJSONLicense = "http://service.eternity.co.th/tires/system/CenterService/getVehicle.php";
    private String urlJSONFormatWhell = "http://service.eternity.co.th/tires/system/CenterService/getFormatVehicle.php";
    private String urlAddCheckList = "http://service.eternity.co.th/tires/system/CenterService/addCheckList.php";

    public String getUrlJSONuser() {
        return urlJSONuser;
    }

    public String getUrlAddCheckList() {
        return urlAddCheckList;
    }

    public String getUrlJSONLicense() {
        return urlJSONLicense;
    }

    public String getUrlJSONFormatWhell() {
        return urlJSONFormatWhell;
    }
}
