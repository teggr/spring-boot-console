welcome = { ->
	if (!crash.context.attributes['spring.environment'].getProperty("spring.main.show_banner", Boolean.class, Boolean.TRUE)) {
		return ""
	}

	// Resolve hostname
	def hostName;
	try {
		hostName = java.net.InetAddress.getLocalHost().getHostName();
	}
	catch (java.net.UnknownHostException ignore) {
		hostName = "localhost";
	}

	// Get Spring Boot version from context
	def version = crash.context.attributes.get("spring.boot.version")

	return """\
	Robin Rules
""";
}

prompt = { ->
	return "% ";
}
