package com.eventer.wallet.mapper;

import com.eventer.wallet.domain.UserBalance;
import org.springframework.stereotype.Component;

@Component
public interface UserBalanceMapper {

    /**
     * Snima podatke o korisniku
     * @param balance
     */
    void save(UserBalance balance);

    /**
     * Azurira podatke o korisniku
     * @param balance
     * @return
     */
    int update(UserBalance balance);

    /**
     * Dohvata korisnika po prosledjenom id-u
     * @param balance
     * @return
     */
    UserBalance findBalance(UserBalance balance);

    /**
     * Dohvata korisnika po prosledjenom id-u
     * @param id
     * @return
     */
    UserBalance getById(Long id);

}
