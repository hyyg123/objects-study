package chapter_04.before;

public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        Money fee = getMovieFee(screening);
        return new Reservation(customer, screening, fee, audienceCount);
    }

    private Money getMovieFee(Screening screening) {
        Movie movie = screening.getMovie();

        boolean discountable = isDiscountable(screening, movie);
        if (discountable) {
            return getDiscountedFee(movie);
        }

        return movie.getFee();
    }

    private Money getDiscountedFee(Movie movie) {
        Money discountAmount = getDiscountAmount(movie);
        return movie.getFee().minus(discountAmount);
    }

    private Money getDiscountAmount(Movie movie) {
        switch (movie.getMovieType()) {
            case AMOUNT_DISCOUNT :
                return movie.getDiscountAmount();
            case PERCENT_DISCOUNT:
                return movie.getFee().times(movie.getDiscountPercent());
            case NONE_DISCOUNT:
                return Money.ZERO;
        }

        return Money.ZERO;
    }

    private boolean isDiscountable(Screening screening, Movie movie) {
        return movie.getDiscountConditions().stream()
                .anyMatch(condition -> isDiscountable(screening, condition));
    }

    private boolean isDiscountable(Screening screening, DiscountCondition condition) {
        if (condition.getType() == DiscountConditionType.PERIOD) {
            return screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                    !condition.getStartTime().isAfter(screening.getWhenScreened().toLocalTime()) &&
                    !condition.getEndTime().isBefore(screening.getWhenScreened().toLocalTime());
        }
        return condition.getSequence() == screening.getSequence();
    }
}
