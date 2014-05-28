package k4unl.minecraft.colorchat.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import k4unl.minecraft.colorchat.lib.config.Constants;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;


public abstract class LocationDoublePacket extends AbstractPacket {
	protected double x, y, z;
	
	public LocationDoublePacket(){}
	
	public LocationDoublePacket(double _x, double _y, double _z){
		x = _x;
		y = _y;
		z = _z;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer){
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer){
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
	}
	
	public NetworkRegistry.TargetPoint getTargetPoint(World world){
		return getTargetPoint(world, Constants.PACKET_UPDATE_DISTANCE);
	}
	
	public NetworkRegistry.TargetPoint getTargetPoint(World world, double updateDistance){
		return new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, updateDistance);
	}
}

