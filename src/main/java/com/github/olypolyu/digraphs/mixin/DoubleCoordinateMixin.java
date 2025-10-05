package com.github.olypolyu.digraphs.mixin;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.net.command.exceptions.CommandExceptions;
import net.minecraft.core.net.command.helpers.DoubleCoordinate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.olypolyu.digraphs.DigraphConfig.digraphs;

@Mixin(value = DoubleCoordinate.class, remap = false, priority = 0)
public class DoubleCoordinateMixin {

	@Inject(method = "parse", at = @At("HEAD"), cancellable = true)
	private static void parse(StringReader reader, CallbackInfoReturnable<DoubleCoordinate> cir) throws CommandSyntaxException {
		if (!reader.canRead()) {
			throw CommandExceptions.incomplete().createWithContext(reader);
		}

		else if (digraphs.contains(reader.peek())) {
			reader.skip();
			if (reader.canRead() && reader.peek() != ' ') {
				cir.setReturnValue(new DoubleCoordinate(true, reader.readDouble()));
			}

			else cir.setReturnValue(new DoubleCoordinate(true, 0.0D));
		}

		else if (reader.peek() != ' ') {
			cir.setReturnValue(new DoubleCoordinate(false, reader.readDouble()));
		}

		else throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().createWithContext(reader);
	}
}
