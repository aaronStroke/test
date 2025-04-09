package mx.loal.pharmacy_admin_api.service;

import mx.loal.pharmacy_admin_api.payload.CashRegisterDto;
import mx.loal.pharmacy_admin_api.payload.request.OpenCashRegister;

public interface CashRegisterService {

    CashRegisterDto openCashRegister(OpenCashRegister openCashRegister);

    CashRegisterDto getCurrentCashRegister();

}
