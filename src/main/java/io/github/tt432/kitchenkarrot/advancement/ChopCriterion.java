package io.github.tt432.kitchenkarrot.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ChopCriterion extends SimpleCriterionTrigger<> {

    @Override
    @NotNull
    protected AbstractCriterionTriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        return new ;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    protected void trigger(ServerPlayer p_66235_, Predicate p_66236_) {
        super.trigger(p_66235_, p_66236_);
    }

    static class ChopCriterionInstance extends AbstractCriterionTriggerInstance{

        public ChopCriterionInstance(ResourceLocation resourceLocation, ContextAwarePredicate predicate) {
            super(resourceLocation, predicate);
        }

        boolean matches()
    }
}
