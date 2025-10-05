package com.github.olypolyu.digraphs.mixin;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.net.command.exceptions.CommandExceptions;
import net.minecraft.core.net.command.helpers.IntegerCoordinate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.olypolyu.digraphs.DigraphConfig.digraphs;

@Mixin(value = IntegerCoordinate.class, remap = false, priority = 0)
public class IntegerCoordinateMixin {

	@Inject(method = "parse", at = @At("HEAD"), cancellable = true)
	private static void parse(StringReader reader, CallbackInfoReturnable<IntegerCoordinate> cir) throws CommandSyntaxException {
		if (!reader.canRead()) {
			throw CommandExceptions.incomplete().createWithContext(reader);
		}

		else if (digraphs.contains(reader.peek())) {
			reader.skip();
			if (reader.canRead() && reader.peek() != ' ') {
				cir.setReturnValue(new IntegerCoordinate(true, reader.readInt()));
			}

			else cir.setReturnValue(new IntegerCoordinate(true, 0));
		}

		else if (reader.peek() != ' ') {
			cir.setReturnValue(new IntegerCoordinate(false, reader.readInt()));
		}

		else throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().createWithContext(reader);
	}

}
