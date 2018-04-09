package net.bndy.wf.config;

import net.bndy.lib.StringHelper;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.spi.MetadataBuildingContext;

public class DatabaseImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {

        String columnName =
            StringHelper.insertUnderscoreBetweenWords(source.getAttributePath().getProperty()).toLowerCase();

        ImplicitBasicColumnNameSource newSource = new ImplicitBasicColumnNameSource() {
            @Override
            public AttributePath getAttributePath()
            {
                return AttributePath.parse(columnName);
            }

            @Override
            public boolean isCollectionElement() {
                return source.isCollectionElement();
            }

            @Override
            public MetadataBuildingContext getBuildingContext()
            {
                return source.getBuildingContext();
            }
        };

        return super.determineBasicColumnName(newSource);
    }
}
