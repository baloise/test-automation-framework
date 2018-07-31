package com.baloise.testautomation.taf.base.types;

public class TafId {

  private static String globalMandant = "int";

  public static String GetGlobalMandant() {
    return globalMandant;
  }

  public static void SetGlobalMandant(String gMandant) {
    if (gMandant != null) {
      globalMandant = gMandant;
    }
  }

  String mandant = "";
  String id = "";
  String detail = "";
  String vpId = "";

  public TafId(String idAndDetail) {
    /*
     * Wenn die Id als String übergeben wird, dann wird erwartet,
     * dass die Id und das Detail mit einem
     * Bindestrich voneinander getrennt werden. Der Mandant darf NIE mit übergeben werden,
     * da er global verwaltet wird
     */
    super();
    mandant = globalMandant;
    if (idAndDetail != null) {
      String[] parts = idAndDetail.split("-");
      id = parts[0].trim();
      if (parts.length > 1) {
        detail = parts[1].trim();
      }
      if (parts.length > 2) {
        vpId = parts[2].trim();
      }
    }
  }

  public TafId(String id, String detail) {
    super();
    this.mandant = globalMandant;
    this.id = id.trim();
    this.detail = detail.trim();
  }

  public TafId(String mandant, String id, String detail) {
    super();
    try {
      String[] mandanten = mandant.trim().split(";");
      if (mandanten.length == 1) {
        this.mandant = mandanten[0].trim();
      }
      if (mandanten.length > 1) {
        for (String m : mandanten) {
          if (m.trim().equalsIgnoreCase(globalMandant)) {
            this.mandant = m.trim();
            break;
          }
        }
      }
      if (mandant.isEmpty()) {
        this.mandant = mandant.trim();
      }
    }
    catch (Exception e) {}
    try {
      this.id = id.trim();
    }
    catch (Exception e) {}
    try {
      this.detail = detail.trim();
    }
    catch (Exception e) {}
  }

  public TafId(String mandant, String id, String detail, String vpId) {
    this(mandant, id, detail);
    setVpId(vpId);
  }

  public String asIdDetailString() {
    return getId() + "-" + getDetail();
  }

  public String asMandantIdDetailString() {
    return (this.mandant + "-" + this.id + "-" + this.detail);
  }

  public boolean equals(TafId id) {
    if (id.getVpId().isEmpty() & getVpId().isEmpty()) {
      if (id.getMandant().equalsIgnoreCase(getMandant()) & id.getId().equalsIgnoreCase(getId())) {
        if (id.getDetail().equalsIgnoreCase("") | getDetail().equalsIgnoreCase("")) {
          // falls Detail = leer --> Kein Vergleich!
          return true;
        }
        return (id.getDetail().equalsIgnoreCase(getDetail()));
      }
      return false;
    }
    boolean mandantIsEqual = (id.getMandant().equalsIgnoreCase(getMandant()));
    boolean idIsEqual = (id.getId().equalsIgnoreCase(getId()));
    boolean detailIsEqual = (id.getDetail().equalsIgnoreCase(getDetail()));
    boolean vpIdIsEqual = (id.getVpId().equalsIgnoreCase(getVpId()));
    return (mandantIsEqual & idIsEqual & detailIsEqual & vpIdIsEqual);
  }

  public String getDetail() {
    if (detail != null) {
      return detail;
    }
    return "";
  }

  public String getId() {
    if (id != null) {
      return id;
    }
    return "";
  }

  public String getMandant() {
    if (mandant != null) {
      return mandant;
    }
    return "";
  }

  public String getVpId() {
    if (vpId != null) {
      return vpId;
    }
    return "";
  }

  public void setVpId(String vpId) {
    try {
      this.vpId = vpId.trim();
    }
    catch (Exception e) {
      vpId = null;
    }
  }

}
