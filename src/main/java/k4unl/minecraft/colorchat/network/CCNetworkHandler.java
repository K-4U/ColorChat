package k4unl.minecraft.colorchat.network;

import k4unl.minecraft.colorchat.lib.config.ModInfo;
import k4unl.minecraft.k4lib.network.NetworkHandler;

/**
 * @author Koen Beckers (K-4U)
 */
public class CCNetworkHandler extends NetworkHandler {

	public CCNetworkHandler() {
		super();
	}


	public void init() {
		int i = 0;
//		getChannel().registerMessage(i++, PackageAllDataToClient.class, PackageAllDataToClient::toBytes, PackageAllDataToClient::new, PackageAllDataToClient::handle);
	}

	@Override
	public String getModId() {
		return ModInfo.ID;
	}

	@Override
	public String getProtocolVersion() {
		return "1";
	}
}
