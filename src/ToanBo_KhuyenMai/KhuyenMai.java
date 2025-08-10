/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToanBo_KhuyenMai;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class KhuyenMai {

    private String Ma_KM;
    private String Ten_KM;
    private String MoTa_KM;
    private String HinhThuc_KM;
    private float GiaTri_YeuCau_KM;
    private float GiaTri_KM;
    private Date Ngay_BD;
    private Date Ngay_KT;
    private boolean TrangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(String Ma_KM, String Ten_KM, String MoTa_KM, String HinhThuc_KM, float GiaTri_YeuCau_KM, float GiaTri_KM, Date Ngay_BD, Date Ngay_KT, boolean TrangThai) {
        this.Ma_KM = Ma_KM;
        this.Ten_KM = Ten_KM;
        this.MoTa_KM = MoTa_KM;
        this.HinhThuc_KM = HinhThuc_KM;
        this.GiaTri_YeuCau_KM = GiaTri_YeuCau_KM;
        this.GiaTri_KM = GiaTri_KM;
        this.Ngay_BD = Ngay_BD;
        this.Ngay_KT = Ngay_KT;
        this.TrangThai = TrangThai;
    }


    public String getMa_KM() {
        return Ma_KM;
    }

    public void setMa_KM(String Ma_KM) {
        this.Ma_KM = Ma_KM;
    }

    public String getTen_KM() {
        return Ten_KM;
    }

    public void setTen_KM(String Ten_KM) {
        this.Ten_KM = Ten_KM;
    }

    public String getMoTa_KM() {
        return MoTa_KM;
    }

    public void setMoTa_KM(String MoTa_KM) {
        this.MoTa_KM = MoTa_KM;
    }

    public String getHinhThuc_KM() {
        return HinhThuc_KM;
    }

    public void setHinhThuc_KM(String HinhThuc_KM) {
        this.HinhThuc_KM = HinhThuc_KM;
    }

    public float getGiaTri_YeuCau_KM() {
        return GiaTri_YeuCau_KM;
    }

    public void setGiaTri_YeuCau_KM(float GiaTri_YeuCau_KM) {
        this.GiaTri_YeuCau_KM = GiaTri_YeuCau_KM;
    }

    public float getGiaTri_KM() {
        return GiaTri_KM;
    }

    public void setGiaTri_KM(float GiaTri_KM) {
        this.GiaTri_KM = GiaTri_KM;
    }

    public Date getNgay_BD() {
        return Ngay_BD;
    }

    public void setNgay_BD(Date Ngay_BD) {
        this.Ngay_BD = Ngay_BD;
    }

    public Date getNgay_KT() {
        return Ngay_KT;
    }

    public void setNgay_KT(Date Ngay_KT) {
        this.Ngay_KT = Ngay_KT;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

}
