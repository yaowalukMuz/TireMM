package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

/**
 * Created by musz on 7/25/2016.
 */
public class ConstantUrl {

    private String urlJSONuser = "";
    private String urlJSONLicense = "http://service.eternity.co.th/tires/system/CenterService/getVehicle.php";
    private String urlJSONFormatWhell = "http://service.eternity.co.th/tires/system/CenterService/getFormatVehicle.php";
    private String imageSource;

    public void setImageSource(String tireId) {
        if (tireId.equals("")) {
            imageSource = "@drawable/tire_null";
        } else {
            imageSource = "@drawable/tire";
        }
    }

    public String getImageSource() {
        return imageSource;
    }

    public String getUrlJSONuser() {
        return urlJSONuser;
    }

    public String getUrlJSONLicense() {
        return urlJSONLicense;
    }

    public String getUrlJSONFormatWhell() {
        return urlJSONFormatWhell;
    }
}
