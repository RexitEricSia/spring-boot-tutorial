package com.rexit.tutorial.service;

import java.util.List;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rexit.tutorial.dto.CampaignPatchDTO;
import com.rexit.tutorial.enums.Error;
import com.rexit.tutorial.exception.BusinessException;
import com.rexit.tutorial.model.Campaign;
import com.rexit.tutorial.repository.CampaignRepository;

import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.QueryTimeoutException;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<Campaign> getAllCampaigns() {

        try {
            return campaignRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_FETCH_ERROR);
        }
    }

    public Campaign getCampaignById(Long id) {

        Optional<Campaign> campaign = null;

        try {
            campaign = campaignRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(Error.CAMPAIGN_FETCH_BY_ID_ERROR);
        }

        if (campaign.isPresent()) {
            return campaign.get();
        } else {
            throw new BusinessException(Error.CAMPAIGN_ID_NOT_FOUND);
        }
    }

    public Campaign createCampaign(Campaign newCampaign) {

        if (campaignRepository.existsByCampaignCode(newCampaign.getCampaignCode())) {
            throw new BusinessException(Error.CAMPAIGN_DUPLICATE_CODE);
        }

        try {
            return campaignRepository.save(newCampaign);
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_CREATE_ERROR);
        }
    }

    public Campaign updateCampaign(Long id, Campaign updatedCampaign) {

        if (!id.equals(updatedCampaign.getId())) {
            throw new BusinessException(Error.CAMPAIGN_ID_MISMATCH);
        }

        if (!campaignRepository.existsById(id)) {
            throw new BusinessException(Error.CAMPAIGN_ID_NOT_FOUND_FOR_UPDATE);
        }

        try {
            return campaignRepository.save(updatedCampaign);
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_UPDATE_ERROR);
        }
    }
    // dev version
    public boolean deleteCampaign(Long id) {

        System.out.println("Feature T001: Checking Business logic A before deletion.");
        if (!campaignRepository.existsById(id)) {
            throw new BusinessException(Error.CAMPAIGN_ID_NOT_FOUND_FOR_DELETE);
        }

        try {
            campaignRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_DELETE_ERROR);
        }
    }

    // propogation
    // - REQUIRED (default) Joins the existing transaction if one exists; otherwise,
    // starts a new one.
    // - REQUIRES_NEW Suspends the current transaction and always starts a new one.
    // - NESTED Executes within a nested transaction if one exists (requires JDBC
    // savepoints support).
    // - SUPPORTS Joins existing transaction if available; otherwise, runs
    // non-transactionally.
    // - NOT_SUPPORTED Suspends any existing transaction and runs
    // non-transactionally.
    // - NEVER Throws an exception if a transaction exists.
    // - MANDATORY Must run within an existing transaction; throws an exception if
    // none exists.

    // isolation
    // - DEFAULT (default) Uses the database’s default isolation level.
    // - READ_UNCOMMITTED Allows dirty reads (least restrictive).
    // - READ_COMMITTED Prevents dirty reads; allows non-repeatable reads and
    // phantom
    // reads.
    // - REPEATABLE_READ Prevents dirty and non-repeatable reads; allows phantom
    // reads.
    // - SERIALIZABLE Fully isolates transactions (most restrictive); avoids all
    // concurrency issues.

    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 30, rollbackFor = Exception.class)
    public Campaign patchCampaign(Long id, CampaignPatchDTO dto) {

        Optional<Campaign> optionalCampaign = null;

        try {
            optionalCampaign = campaignRepository.findById(id);
        } catch (QueryTimeoutException | LockTimeoutException e) {
            throw new BusinessException(Error.CAMPAIGN_LOCK_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(Error.CAMPAIGN_FETCH_BY_ID_ERROR);
        }

        if (!optionalCampaign.isPresent()) {
            throw new BusinessException(Error.CAMPAIGN_ID_NOT_FOUND);
        }

        Campaign campaign = optionalCampaign.get();

        try {
            if (dto.getName() != null)
                campaign.setName(dto.getName());
            if (dto.getDescription() != null)
                campaign.setDescription(dto.getDescription());
            if (dto.getOrganiserEmail() != null)
                campaign.setOrganiserEmail(dto.getOrganiserEmail());
            if (dto.getAge() != 0)
                campaign.setAge(dto.getAge());
            if (dto.getDiscountPercentage() != 0)
                campaign.setDiscountPercentage(dto.getDiscountPercentage());
            if (dto.getStartDate() != null)
                campaign.setStartDate(dto.getStartDate());
            if (dto.getEndDate() != null)
                campaign.setEndDate(dto.getEndDate());
            if (dto.getHallRentalPrice() != null)
                campaign.setHallRentalPrice(dto.getHallRentalPrice());

            return campaignRepository.save(campaign);

        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
            throw new BusinessException(Error.CAMPAIGN_OPTIMISTIC_LOCK);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 30, rollbackFor = Exception.class)
    public Campaign newPatchCampaign(Long id, CampaignPatchDTO dto) throws Exception {
        throw new Exception("new transaction error");
    }
}
