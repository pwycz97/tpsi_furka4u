/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import wizut.tpsi.ogloszenia.jpa.BodyStyle;
import wizut.tpsi.ogloszenia.jpa.CarManufacturer;
import wizut.tpsi.ogloszenia.jpa.CarModel;
import wizut.tpsi.ogloszenia.jpa.FuelType;
import wizut.tpsi.ogloszenia.jpa.Offer;
import wizut.tpsi.ogloszenia.web.OfferFilter;

/**
 *
 * @author piotr
 */
@Service
@Transactional
public class OffersService {

    @PersistenceContext
    private EntityManager em;

    public CarManufacturer getCarManufacturer(int id) {
        return em.find(CarManufacturer.class, id);
    }

    public CarModel getCarModel(int id) {
        return em.find(CarModel.class, id);
    }

    public Offer getOffer(int id) {
        return em.find(Offer.class, id);
    }

    public List<CarManufacturer> getCarManufacturers() {
        String jpql = "select cm from CarManufacturer cm order by cm.name";
        TypedQuery<CarManufacturer> query = em.createQuery(jpql, CarManufacturer.class);
        List<CarManufacturer> result = query.getResultList();
        return result;
    }

    public List<BodyStyle> getBodyStyles() {
        String jpql = "select bs from BodyStyle bs order by bs.name";
        TypedQuery<BodyStyle> query = em.createQuery(jpql, BodyStyle.class);
        List<BodyStyle> result = query.getResultList();
        return result;
    }

    public List<FuelType> getFuelTypes() {
        String jpql = "select ft from FuelType ft order by ft.name";
        TypedQuery<FuelType> query = em.createQuery(jpql, FuelType.class);
        List<FuelType> result = query.getResultList();
        return result;
    }

    public List<CarModel> getCarModels() {
        String jpql = "select cm from CarModel cm order by cm.name";
        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        List<CarModel> result = query.getResultList();
        return result;
    }

    public List<CarModel> getCarModels(int manufacturerId) {
        String jpql = "select cm from CarModel cm where cm.manufacturer.id = :id order by cm.name";

        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }

    public List<Offer> getOffers() {
       String jpql = "select cm from Offer cm order by cm.title";

       TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);

       return query.getResultList();
    }

    public List<Offer> getOffersByModel(int modelId) {
        String jpql = "select obm from Offer obm where obm.model.id = :id";

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", modelId);

        return query.getResultList();
    }

    public List<Offer> getOffersByManufacturer(int manufacturerId) {
        String jpql = "select off from Offer off inner join off.model cm on cm.manufacturer.id = :id ";
        
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }

    public Offer createOffer(Offer offer) {
        em.persist(offer);
        return offer;
    }

    public Offer deleteOffer(Integer id) {
        Offer offer = em.find(Offer.class, id);
        em.remove(offer);
        return offer;
    }

    public Offer saveOffer(Offer offer) {
        return em.merge(offer);
    }
    
    public List<Offer> getOffers(OfferFilter offerFilter)
    {
        String jpql = "select off from Offer off where ";
        boolean flag_if = false;
        StringBuilder stringBuffer = new StringBuilder(jpql);

        if (offerFilter.getManufacturerId() != null) {
            stringBuffer.append("off.carModel.manufacturer.id = :manuacturerId ");  
            flag_if = true;
        }
        
        if (offerFilter.getModelId() != null) {
            if (flag_if) {
                stringBuffer.append("AND off.carModel.id = :modelId ");
            } else {
                stringBuffer.append("off.carModel.id = :modelId ");
            }
            flag_if = true;
        }
        
        if (offerFilter.getFuelTypeId() != null) {
            if (flag_if) {
                stringBuffer.append("AND off.fuelType.id = :fuelId ");
            } else {
                stringBuffer.append("off.fuelType.id = :fuelId ");
            }
            flag_if = true;
        }
        
        if (offerFilter.getYearFrom() != null) {
            if (flag_if) {
                stringBuffer.append("AND off.year >= :yearFrom ");
            } else {
                stringBuffer.append("off.year >= :yearFrom ");
            }
            flag_if = true;
        }
        
        if (offerFilter.getYearTo() != null) {
            if (flag_if) {
                stringBuffer.append("AND off.year <= :yearTo ");
            } else {
                stringBuffer.append("off.year <= :yearTo ");
            }
            flag_if = true;
        }
        
        if (offerFilter.getPriceFrom() != null) {
            if (flag_if) {
                stringBuffer.append("AND off.price >= :priceFrom ");
            } else {
                stringBuffer.append("off.price >= :priceFrom ");
            }
            flag_if = true;
        }
        
        if (offerFilter.getPriceTo() != null) {
            if (flag_if) {
                stringBuffer.append("AND off.price <= :priceTo ");
            } else {
                stringBuffer.append("off.price <= :priceTo ");
            }
            flag_if = true;
        }
        
        if (offerFilter.getOpis() != null) {
            if (flag_if) {
                stringBuffer.append("AND LOWER(off.description) LIKE :opis ");
            } else {
                stringBuffer.append("LOWER(off.description) LIKE :opis ");
            }
            flag_if = true;
        }

        if (!flag_if) {
            stringBuffer.append("1=1 order by off.");
            
            if(offerFilter.getSort() != null){
                switch (offerFilter.getSort()) {
                    case "price_asc":
                        stringBuffer.append("price asc");
                        break;
                    case "price_desc":
                        stringBuffer.append("price desc");
                        break;
                    case "date_asc":
                        stringBuffer.append("data_dodania asc");
                        break;
                    case "date_desc":
                        stringBuffer.append("data_dodania desc");
                        break;
                    case "year_asc":
                        stringBuffer.append("year asc");
                        break;
                    case "year_desc":
                        stringBuffer.append("year desc");
                        break;
                    default:
                        stringBuffer.append("title");
                        break;
                }
            }else{
                stringBuffer.append("title");
            }
            
            jpql = stringBuffer.toString();
            TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
            return query.getResultList();  
        }

        stringBuffer.append("order by off.");
        
        if(offerFilter.getSort() != null){
            switch (offerFilter.getSort()) {
                    case "price_asc":
                        stringBuffer.append("price asc");
                        break;
                    case "price_desc":
                        stringBuffer.append("price desc");
                        break;
                    case "date_asc":
                        stringBuffer.append("data_dodania asc");
                        break;
                    case "date_desc":
                        stringBuffer.append("data_dodania desc");
                        break;
                    case "year_asc":
                        stringBuffer.append("year asc");
                        break;
                    case "year_desc":
                        stringBuffer.append("year desc");
                        break;
                    default:
                        stringBuffer.append("title");
                        break;
        }
        }else{
            stringBuffer.append("title");
        }
        
        jpql = stringBuffer.toString();
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
         
        if (offerFilter.getManufacturerId() != null)
            query.setParameter("manufacturerId", offerFilter.getManufacturerId());
        if (offerFilter.getModelId() != null)
            query.setParameter("modelId", offerFilter.getModelId());
        if (offerFilter.getFuelTypeId() != null)
            query.setParameter("fuelId", offerFilter.getFuelTypeId());
        if (offerFilter.getYearFrom() != null)
            query.setParameter("yearFrom", offerFilter.getYearFrom());
        if (offerFilter.getYearTo() != null)
            query.setParameter("yearTo", offerFilter.getYearTo());
        if (offerFilter.getPriceFrom() != null)    
            query.setParameter("priceFrom", offerFilter.getPriceFrom());
        if (offerFilter.getPriceTo() != null)
            query.setParameter("priceTo", offerFilter.getPriceTo());
        if (offerFilter.getOpis() != null)
        {
            StringBuilder opis = new StringBuilder("%");
            opis.append(offerFilter.getOpis().toLowerCase());
            opis.append("%");
            
            query.setParameter("opis", opis.toString());
        }
        return query.getResultList();
    }
}
