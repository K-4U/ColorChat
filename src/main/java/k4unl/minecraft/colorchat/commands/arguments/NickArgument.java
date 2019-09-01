package k4unl.minecraft.colorchat.commands.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Koen Beckers (K-4U)
 */
public class NickArgument implements ArgumentType<User> {

	public static final DynamicCommandExceptionType NICK_INVALID = new DynamicCommandExceptionType((p_208659_0_) -> {
		return new TranslationTextComponent("argument.nick.invalid", p_208659_0_);
	});
	private static final Collection<String> EXAMPLES = Arrays.asList("Leftie", "Righty");

	public static NickArgument nick() {
		return new NickArgument();
	}

	public static User getUser(CommandContext<CommandSource> context, String name) {
		return context.getArgument(name, User.class);
	}

	@Override
	public User parse(StringReader reader) throws CommandSyntaxException {
		String s = reader.readUnquotedString();
		User user = Users.getUserByNick(s);
		if (null == user) {
			throw NICK_INVALID.create(s);
		}
		return user;
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		return ISuggestionProvider.suggest(Users.getNickNames(), builder);
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}

}
