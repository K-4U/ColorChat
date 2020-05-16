package k4unl.minecraft.colorchat.commands.arguments;

/**
 * @author Koen Beckers (K-4U)
 */
public class GroupArgument { //implements ArgumentType<Group> {
/*
	public static final DynamicCommandExceptionType GROUP_INVALID = new DynamicCommandExceptionType((p_208659_0_) -> {
		return new TranslationTextComponent("argument.group.invalid", p_208659_0_);
	});
	private static final Collection<String> EXAMPLES = Arrays.asList("mods", "ops");

	public static GroupArgument group() {
		return new GroupArgument();
	}

	public static Group getGroup(CommandContext<CommandSource> context, String name) {
		return context.getArgument(name, Group.class);
	}

	@Override
	public Group parse(StringReader reader) throws CommandSyntaxException {
		String s = reader.readUnquotedString();
		Group group = Groups.getGroupByName(s);
		if (null == group) {
			throw GROUP_INVALID.create(s);
		}
		return group;
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		return ISuggestionProvider.suggest(Groups.getGroupNames(), builder);
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}*/
}
