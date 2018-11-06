//
// From Choonster's TestMod3!
//

package org.orecruncher.lib.capability;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * A simple implementation of {@link ICapabilityProvider} that supports a single
 * {@link Capability} handler instance.
 *
 * @author Choonster
 */
public class CapabilityProviderSimple<H> implements ICapabilityProvider {

	/**
	 * The {@link Capability} instance to provide the handler for.
	 */
	protected final Capability<H> capability;

	/**
	 * The {@link EnumFacing} to provide the handler for.
	 */
	protected final EnumFacing facing;

	/**
	 * The handler instance to provide.
	 */
	protected final H instance;

	public CapabilityProviderSimple(final Capability<H> capability, @Nullable final EnumFacing facing,
			@Nullable final H instance) {
		this.capability = capability;
		this.facing = facing;
		this.instance = instance;
	}

	@Deprecated
	public CapabilityProviderSimple(@Nullable final H instance, final Capability<H> capability,
			@Nullable final EnumFacing facing) {
		this(capability, facing, instance);
	}

	/**
	 * Determines if this object has support for the capability in question on the
	 * specific side. The return value of this MIGHT change during runtime if this
	 * object gains or looses support for a capability.
	 * <p>
	 * Example: A Pipe getting a cover placed on one side causing it loose the
	 * Inventory attachment function for that side.
	 * <p>
	 * This is a light weight version of getCapability, intended for metadata uses.
	 *
	 * @param capability
	 *                       The capability to check
	 * @param facing
	 *                       The Side to check from: CAN BE NULL. Null is defined to
	 *                       represent 'internal' or 'self'
	 * @return True if this object supports the capability.
	 */
	@Override
	public boolean hasCapability(final Capability<?> capability, @Nullable final EnumFacing facing) {
		return capability == getCapability();
	}

	/**
	 * Retrieves the handler for the capability requested on the specific side. The
	 * return value CAN be null if the object does not support the capability. The
	 * return value CAN be the same for multiple faces.
	 *
	 * @param capability
	 *                       The capability to check
	 * @param facing
	 *                       The Side to check from: CAN BE NULL. Null is defined to
	 *                       represent 'internal' or 'self'
	 * @return The handler if this object supports the capability.
	 */
	@Override
	@Nullable
	public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing) {
		if (capability == getCapability()) {
			return getCapability().cast(getInstance());
		}

		return null;
	}

	/**
	 * Get the {@link Capability} instance to provide the handler for.
	 *
	 * @return The Capability instance
	 */
	public final Capability<H> getCapability() {
		return this.capability;
	}

	/**
	 * Get the {@link EnumFacing} to provide the handler for.
	 *
	 * @return The EnumFacing to provide the handler for
	 */
	@Nullable
	public EnumFacing getFacing() {
		return this.facing;
	}

	/**
	 * Get the handler instance.
	 *
	 * @return The handler instance
	 */
	@Nullable
	public final H getInstance() {
		return this.instance;
	}
}