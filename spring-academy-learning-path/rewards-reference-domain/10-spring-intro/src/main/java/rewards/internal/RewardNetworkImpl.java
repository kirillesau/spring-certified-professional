package rewards.internal;

import common.money.MonetaryAmount;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.RewardNetwork;
import rewards.internal.account.Account;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.Restaurant;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;

/**
 * Rewards an Account for Dining at a Restaurant.
 * <p>
 * The sole Reward Network implementation. This object is an application-layer service responsible for coordinating with
 * the domain-layer to carry out the process of rewarding benefits to accounts for dining.
 * <p>
 * Said in other words, this class implements the "reward account for dining" use case.
 * <p>
 * In this lab, you are going to exercise the following:
 * - Understanding internal operations that need to be performed to implement
 * "rewardAccountFor" method of the "RewardNetworkImpl" class
 * - Writing test code using stub implementations of dependencies
 * - Writing both target code and test code without using Spring framework
 * <p>
 * Review the Rewards Application document (Refer to the lab document)
 * Review project dependencies (Refer to the lab document)
 * Review Rewards Commons project (Refer to the lab document)
 * Review RewardNetwork interface and RewardNetworkImpl class below
 * Review the RewardNetworkImpl configuration logic (Refer to the lab document)
 * Review sequence diagram (Refer to the lab document)
 */
public class RewardNetworkImpl implements RewardNetwork {

    private AccountRepository accountRepository;
    private RestaurantRepository restaurantRepository;

    private RewardRepository rewardRepository;

    /**
     * Creates a new reward network.
     *
     * @param accountRepository    the repository for loading accounts to reward
     * @param restaurantRepository the repository for loading restaurants that determine how much to reward
     * @param rewardRepository     the repository for recording a record of successful reward transactions
     */
    public RewardNetworkImpl(AccountRepository accountRepository, RestaurantRepository restaurantRepository,
                             RewardRepository rewardRepository) {
        this.accountRepository = accountRepository;
        this.restaurantRepository = restaurantRepository;
        this.rewardRepository = rewardRepository;
    }

    public RewardConfirmation rewardAccountFor(Dining dining) {
        Account account = accountRepository.findByCreditCard(dining.getCreditCardNumber());
        Restaurant restaurant = restaurantRepository.findByMerchantNumber(dining.getMerchantNumber());
        MonetaryAmount amount = restaurant.calculateBenefitFor(account, dining);
        AccountContribution accountContribution = account.makeContribution(amount);
        accountRepository.updateBeneficiaries(account);
        return rewardRepository.confirmReward(accountContribution, dining);
    }
}