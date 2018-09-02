package com.nixplugin.warp;

public class Warp {
	
	private double x, y, z;
	private String name, world;
	private float pitch, yaw;
	
	public Warp (String name, String world, double x, double y, double z, float pitch, float yaw) {
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public String toString(){
		return new String(this.name + " (world :" + this.world + ", x:" + this.x + ", y:" + this.y 
				+ ", z:" + this.z + ", pitch:" + this.pitch + ", yaw:" + this.yaw + ")");
	}

	public String getWorld() {
		return world;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public String getName() {
		return name;
	}
	
	public float getPitch(){
		return pitch;
	}
	
	public float getYaw(){
		return yaw;
	}

}
